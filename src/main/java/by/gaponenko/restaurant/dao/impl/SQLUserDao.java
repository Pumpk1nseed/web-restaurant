package by.gaponenko.restaurant.dao.impl;

import by.gaponenko.restaurant.bean.RegistrationUserData;
import by.gaponenko.restaurant.bean.User;
import by.gaponenko.restaurant.bean.criteria.Criteria;
import by.gaponenko.restaurant.controller.RequestParameterName;
import by.gaponenko.restaurant.dao.DaoException;
import by.gaponenko.restaurant.dao.UserDao;
import by.gaponenko.restaurant.dao.pool.ConnectionPool;
import by.gaponenko.restaurant.service.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SQLUserDao implements UserDao {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    ConnectionPool connectionPool = ConnectionPool.getInstance();
    private static final String STATUS = "status";

    private static final String FIND_AUTHORIZED_USER = "SELECT * FROM users WHERE login = ? AND password = ?;";
    private static final String FIND_USER_BY_LOGIN = "SELECT * FROM users WHERE login = ?;";
    private static final String FIND_USER_DETAILS_BY_CRITERIA = "SELECT * FROM users_details WHERE ";
    private static final String GET_USERS_DETAILS = "SELECT ud.id_user, u.login, ud.name, ud.surname, ud.last_name, ud.telephone_number, ud.address, ud.email, ud.date_of_birth, r.title, u.password\n" +
            "FROM users_details ud LEFT JOIN users u on ud.id_user = u.id_user\n" +
            "LEFT JOIN roles r on u.id_role = r.id_role\n" +
            "where u.status = \"active\";";
    private static final String FIND_ROLE = "SELECT * FROM roles WHERE title = ?;";
    private static final String UPDATE_USER_PASSWORD = "UPDATE users SET password = ? WHERE id_user=?;";
    private static final String UPDATE_USER = "UPDATE users SET login = ?, id_role = ? WHERE id_user=?;";
    private static final String UPDATE_USER_DETAILS = "UPDATE users_details SET name = ?, surname = ?, last_name = ?, telephone_number = ?, email = ?, address = ? WHERE id_user=?;";
    private static final String FIND_USERDATA_BY_ID = "SELECT * FROM users_details WHERE id_user = ?;";
    private static final String ADD_NEW_USER = "INSERT INTO users (login, password, id_role, status) VALUES(?,?,?,?);";
    private static final String REMOVE_USER_BY_CRITERIA = "UPDATE users SET status='removed' WHERE ";
    private static final String REGISTER_USER_INFO = "INSERT INTO users_details(id_user, name, surname, last_name, date_of_birth, telephone_number, email, address) VALUES(?,?,?,?,?,?,?,?)";
    private static final String AND = "AND ";
    private static final String REMOVED = "removed";

    @Override
    public User authorization(String login, String pass) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        User user = new User();

        try {
            connection = connectToDataBase(connection);

            preparedStatement = connection.prepareStatement(FIND_AUTHORIZED_USER);
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, pass);
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()) {
                log.info("Attempt to log in with incorrect data. Login :{}", login);
                throw new DaoException("Wrong login or password");
            }

            if (resultSet.getString(STATUS) == REMOVED) {
                log.info("Attempt to log in from deleted user. Login :{}", login);
                throw new DaoException("Sorry, your profile was deleted.");
            }

            user.setIdUser(resultSet.getInt(1));
            user.setLogin(resultSet.getString(2));
            user.setPassword(resultSet.getString(3));
            user.setIdRole(resultSet.getInt(4));

            return user;

        } catch (SQLException e) {
            log.error("Error working with statements while sign in", e);
            throw new DaoException("Error while working with database while sign in", e);
        } finally {
            try {
                connectionPool.closeConnection(connection, preparedStatement, resultSet);
            } catch (SQLException e) {
                log.error("Error while close connection...", e);
            }
        }
    }

    @Override
    public boolean registration(RegistrationUserData userData) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatementForRole;

        try {
            connection = connectToDataBase(connection);
            connection.setAutoCommit(false);

            resultSet = findUserByLogin(connection, userData.getLogin());

            // проверяем, есть ли пользователь с таким логином
            if (resultSet.next()) {
                return false;
            }

            //если введена роль
            int idRole;
            preparedStatementForRole = connection.prepareStatement(FIND_ROLE);
            preparedStatementForRole.setString(1, userData.getRole());
            resultSet = preparedStatementForRole.executeQuery();
            resultSet.next();
            idRole = resultSet.getInt(1);

            preparedStatement = connection.prepareStatement(ADD_NEW_USER);
            preparedStatement.setString(1, userData.getLogin());
            preparedStatement.setString(2, userData.getPassword());
            preparedStatement.setInt(3, idRole);
            preparedStatement.setString(4, "active");
            preparedStatement.executeUpdate();

            resultSet = findUserByLogin(connection, userData.getLogin());
            resultSet.next();

            preparedStatement = connection.prepareStatement(REGISTER_USER_INFO);
            preparedStatement.setInt(1, resultSet.getInt(1));
            preparedStatement.setString(2, userData.getName());
            preparedStatement.setString(3, userData.getSurname());
            preparedStatement.setString(4, userData.getLastName());
            preparedStatement.setString(5, userData.getDateOfBirth());
            preparedStatement.setString(6, userData.getTelephoneNumber());
            preparedStatement.setString(7, userData.getEmail());
            preparedStatement.setString(8, userData.getAddress());
            preparedStatement.executeUpdate();

            connection.commit();
            return true;

        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                log.error("Error rollback while register new User", ex);
                throw new DaoException("Error rollback while register new User", ex);
            }
            log.error("Error working with statements while register new User", e);
            throw new DaoException("Error while register new User", e);
        } finally {
            try {
                connectionPool.closeConnection(connection, preparedStatement, resultSet);
            } catch (SQLException e) {
                log.error("Error while close connection...", e);
            }
        }
    }

    public RegistrationUserData loadUserDataByLogin(String login) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        RegistrationUserData userData = null;

        try {
            connection = connectToDataBase(connection);


            resultSet = findUserByLogin(connection, login);

            if (!resultSet.next()) {
                return userData;
            }
            String idRole = String.valueOf(resultSet.getInt(4));

            preparedStatement = connection.prepareStatement(FIND_USERDATA_BY_ID);
            preparedStatement.setString(1, resultSet.getString(1));
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                userData = new RegistrationUserData();
                userData.setIdUser(resultSet.getInt(1));
                userData.setName(resultSet.getString(2));
                userData.setDateOfBirth(resultSet.getString(3));
                userData.setTelephoneNumber(resultSet.getString(4));
                userData.setAddress(resultSet.getString(5));
                userData.setEmail(resultSet.getString(6));
                userData.setSurname(resultSet.getString(7));
                userData.setLastName(resultSet.getString(8));
                userData.setRole(idRole);
            }

            return userData;

        } catch (SQLException e) {
            log.error("Error working with statements while loading user info");
            throw new DaoException("Error when trying to create a statement user find query", e);
        } finally {
            try {
                connectionPool.closeConnection(connection, preparedStatement, resultSet);
            } catch (SQLException e) {
                log.error("Error while close connection...", e);
            }
        }
    }

    @Override
    public List<RegistrationUserData> find(Criteria criteria) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        Map<String, Object> criteriaMap = criteria.getCriteria();
        List<RegistrationUserData> usersData = null;

        try {
            connection = connectToDataBase(connection);

            StringBuilder queryUserDataBuilder = new StringBuilder(FIND_USER_DETAILS_BY_CRITERIA);
            for (String criteriaName : criteriaMap.keySet()) {
                queryUserDataBuilder.append(String.format("%s=? %s", criteriaName.toLowerCase(), AND));
            }

            queryUserDataBuilder = new StringBuilder(queryUserDataBuilder.substring(0, queryUserDataBuilder.length() - AND.length()));

            preparedStatement = connection.prepareStatement(queryUserDataBuilder.toString());

            int i = 1;
            for (Object value : criteriaMap.values()) {
                preparedStatement.setString(i, value.toString());
                i++;
            }

            resultSet = preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                return usersData;
            }

            usersData = new ArrayList<>();
            while (resultSet.next()) {
                RegistrationUserData userData = new RegistrationUserData();
                userData.setIdUser(resultSet.getInt(1));
                userData.setName(resultSet.getString(2));
                userData.setDateOfBirth(resultSet.getString(3));
                userData.setTelephoneNumber(resultSet.getString(4));
                userData.setAddress(resultSet.getString(5));
                userData.setEmail(resultSet.getString(6));
                userData.setSurname(resultSet.getString(7));
                userData.setLastName(resultSet.getString(8));
                usersData.add(userData);
            }

            return usersData;

        } catch (SQLException e) {
            log.error("Error working with statements while find usersData by criteria");
            throw new DaoException("Error when trying to create a statement user find query", e);
        } finally {
            try {
                connectionPool.closeConnection(connection, preparedStatement, resultSet);
            } catch (SQLException e) {
                log.error("Error while close connection...", e);
            }
        }
    }

    @Override
    public List<RegistrationUserData> getUsers() throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        List<RegistrationUserData> usersData = null;

        try {
            connection = connectToDataBase(connection);
            preparedStatement = connection.prepareStatement(GET_USERS_DETAILS);
            resultSet = preparedStatement.executeQuery();
            usersData = new ArrayList<>();
            while (resultSet.next()) {
                RegistrationUserData userData = new RegistrationUserData();
                userData.setIdUser(resultSet.getInt(1));
                userData.setLogin(resultSet.getString(2));
                userData.setName(resultSet.getString(3));
                userData.setSurname(resultSet.getString(4));
                userData.setLastName(resultSet.getString(5));
                userData.setTelephoneNumber(resultSet.getString(6));
                userData.setAddress(resultSet.getString(7));
                userData.setEmail(resultSet.getString(8));
                userData.setDateOfBirth(resultSet.getString(9));
                userData.setRole(resultSet.getString(10));
                userData.setPassword(resultSet.getString(11));
                usersData.add(userData);
            }

            return usersData;

        } catch (SQLException e) {
            log.error("Error working with statements while find usersData by criteria");
            throw new DaoException("Error when trying to create a statement user find query", e);
        } finally {
            try {
                connectionPool.closeConnection(connection, preparedStatement, resultSet);
            } catch (SQLException e) {
                log.error("Error while close connection...", e);
            }
        }

    }

    @Override
    public boolean updateUserData(RegistrationUserData newUserData) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectToDataBase(connection);
            connection.setAutoCommit(false);

            if (newUserData.getLogin() != null && newUserData.getRole() != null) {
                int idRole;
                preparedStatement = connection.prepareStatement(FIND_ROLE);
                preparedStatement.setString(1, newUserData.getRole());
                resultSet = preparedStatement.executeQuery();
                resultSet.next();
                idRole = resultSet.getInt(1);

                preparedStatement = connection.prepareStatement(UPDATE_USER);
                preparedStatement.setString(1, newUserData.getLogin());
                preparedStatement.setInt(2, idRole);
                preparedStatement.setInt(3, newUserData.getIdUser());
                preparedStatement.executeUpdate();
            }

            if (newUserData.getPassword() != "" && newUserData.getPassword() != null) {
                preparedStatement = connection.prepareStatement(UPDATE_USER_PASSWORD);
                preparedStatement.setString(1, newUserData.getPassword());
                preparedStatement.setInt(2, newUserData.getIdUser());
                preparedStatement.executeUpdate();
            }

            preparedStatement = connection.prepareStatement(UPDATE_USER_DETAILS);
            preparedStatement.setString(1, newUserData.getName());
            preparedStatement.setString(2, newUserData.getSurname());
            preparedStatement.setString(3, newUserData.getLastName());
            preparedStatement.setString(4, newUserData.getTelephoneNumber());
            preparedStatement.setString(5, newUserData.getEmail());
            preparedStatement.setString(6, newUserData.getAddress());
            preparedStatement.setInt(7, newUserData.getIdUser());
            preparedStatement.executeUpdate();

            connection.commit();
            return true;

        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                log.error("Error rollback while update user info", ex);
                throw new DaoException("Error rollback while update user info", ex);
            }
            log.error("Error working with statements while update user info", e);
            throw new DaoException("Error while update user info", e);
        } finally {
            try {
                connectionPool.closeConnection(connection, preparedStatement, resultSet);
            } catch (SQLException e) {
                log.error("Error while close connection...", e);
            }
        }

    }

    @Override
    public int removeUser(Criteria criteria) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        Map<String, Object> criteriaMap = criteria.getCriteria();

        try {
            connection = connectToDataBase(connection);

            StringBuilder queryDishBuilder = new StringBuilder(REMOVE_USER_BY_CRITERIA);
            for (String criteriaName : criteriaMap.keySet()) {
                queryDishBuilder.append(String.format("%s=? %s", criteriaName.toLowerCase(), AND));
            }
            queryDishBuilder = new StringBuilder(queryDishBuilder.substring(0, queryDishBuilder.length() - AND.length()));

            preparedStatement = connection.prepareStatement(queryDishBuilder.toString());
            int i = 1;
            for (Object value : criteriaMap.values()){
                preparedStatement.setString(i, value.toString());
                i++;
            }

            return preparedStatement.executeUpdate();

        } catch (SQLException e) {
            log.error("Error when working with statements while removing user", e);
            throw new DaoException("Error while removing user", e);
        } finally {
            try {
                connectionPool.closeConnection(connection, preparedStatement, null);
            } catch (SQLException e) {
                log.error("Error while close connection...", e);
            }
        }
    }

    private ResultSet findUserByLogin(Connection connection, String login) throws DaoException {
        PreparedStatement preparedStatement;
        ResultSet resultSet;

        try {
            preparedStatement = connection.prepareStatement(FIND_USER_BY_LOGIN);
            preparedStatement.setString(1, login);
            resultSet = preparedStatement.executeQuery();

            return resultSet;
        } catch (SQLException e) {
            log.error("Error working with statements while find user by login");
            throw new DaoException("Error when trying to create a statement user find by login query", e);
        }
    }

    private Connection connectToDataBase(Connection connection) throws DaoException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        try {
            connection = connectionPool.takeConnection();
        } catch (InterruptedException | SQLException | ClassNotFoundException e) {
            log.error("Error while getting connection from connection pool queue", e);
            throw new DaoException("Error taking connection to database", e);
        }
        return connection;
    }
}
