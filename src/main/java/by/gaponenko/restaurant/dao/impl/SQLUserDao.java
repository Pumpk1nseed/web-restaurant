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
    private static final String STATUS = "status";

    private static final String FIND_AUTHORIZED_USER = "SELECT * FROM users WHERE login = ? AND password = ?;";
    private static final String FIND_USER_BY_LOGIN = "SELECT * FROM users WHERE login = ?;";
    private static final String FIND_USER_DETAILS_BY_CRITERIA = "SELECT * FROM users_details WHERE ";
    private static final String GET_USERS_DETAILS = "SELECT * FROM users_details";
    private static final String FIND_ROLE = "SELECT * FROM roles WHERE title = ?;";

    private static final String UPDATE_USER_PASSWORD = "UPDATE users SET password = ? WHERE id_user=?;";
    private static final String UPDATE_USER_DETAILS = "UPDATE users_details SET name = ?, surname = ?, last_name = ?, telephone_number = ?, email = ?, address = ? WHERE id_user=?;";
    private static final String FIND_USERDATA_BY_ID = "SELECT * FROM users_details WHERE id_user = ?;";
    private static final String ADD_NEW_USER = "INSERT INTO users (login, password, id_role, status) VALUES(?,?,?,?);";
    private static final String REGISTER_USER_INFO = "INSERT INTO users_details(id_user, name, surname, last_name, date_of_birth, telephone_number, email, address) VALUES(?,?,?,?,?,?,?,?)";

    private static final String AND = "AND ";
    private Connection connection;

    @Override
    public User authorization(String login, String pass) throws DaoException {
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        User user = new User();

        try {
            connection = connectToDataBase();

            preparedStatement = connection.prepareStatement(FIND_AUTHORIZED_USER);
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, pass);
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()) {
                log.info("Attempt to log in with incorrect data. Login :{}", login);
                throw new DaoException("Wrong login or password");
            }

            if (resultSet.getString(STATUS) == "inactive") {
                log.info("Attempt to log in from deleted user. Login :{}", login);
                throw new DaoException("Sorry, your profile was deleted.");
            }

            user.setIdUser(resultSet.getInt(1));
            user.setLogin(resultSet.getString(2));
            user.setPassword(resultSet.getString(3));
            user.setIdRole(resultSet.getInt(4));

            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            log.error("Error working with statements while sign in", e);
            throw new DaoException("Error while working with database while sign in", e);
        }
        return user;
    }

    @Override
    public boolean registration(RegistrationUserData userData) throws DaoException {
        PreparedStatement preparedStatementForUser;
        PreparedStatement preparedStatementForUserInfo;
        PreparedStatement preparedStatementForRole;
        ResultSet resultSet;
        int idRole;

        try {
            connection = connectToDataBase();
            connection.setAutoCommit(false);

            resultSet = findUserByLogin(connection, userData.getLogin());

            // проверяем, есть ли пользователь с таким логином
            if (resultSet.next()) {
                return false;
            }

            //если введена роль
/*            preparedStatementForRole = connection.prepareStatement(FIND_ROLE);
            preparedStatementForRole.setString(1, userData.getRole());
            resultSet = preparedStatementForRole.executeQuery();
            resultSet.next();
            idRole = resultSet.getInt(1);*/

            preparedStatementForUser = connection.prepareStatement(ADD_NEW_USER);
            preparedStatementForUser.setString(1, userData.getLogin());
            preparedStatementForUser.setString(2, userData.getPassword());
            preparedStatementForUser.setInt(3, 1);
            preparedStatementForUser.setString(4, "active");
            preparedStatementForUser.executeUpdate();

            resultSet = findUserByLogin(connection, userData.getLogin());
            resultSet.next();

            preparedStatementForUserInfo = connection.prepareStatement(REGISTER_USER_INFO);
            preparedStatementForUserInfo.setInt(1, resultSet.getInt(1));
            preparedStatementForUserInfo.setString(2, userData.getName());
            preparedStatementForUserInfo.setString(3, userData.getSurname());
            preparedStatementForUserInfo.setString(4, userData.getLastName());
            preparedStatementForUserInfo.setString(5, userData.getDateOfBirth());
            preparedStatementForUserInfo.setString(6, userData.getTelephoneNumber());
            preparedStatementForUserInfo.setString(7, userData.getEmail());
            preparedStatementForUserInfo.setString(8, userData.getAddress());
            preparedStatementForUserInfo.executeUpdate();

            connection.commit();
            resultSet.close();
            preparedStatementForUser.close();
            preparedStatementForUserInfo.close();
            connection.close();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                log.error("Error rollback while register new User", ex);
                throw new DaoException("Error rollback while register new User", ex);
            }
            log.error("Error working with statements while register new User", e);
            throw new DaoException("Error while register new User", e);
        }
        return true;
    }

    public RegistrationUserData loadUserDataByLogin(String login) throws DaoException {

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        RegistrationUserData userData = null;

        try {
            connection = connectToDataBase();

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
            resultSet.close();
            preparedStatement.close();
            connection.close();

        } catch (SQLException e) {
            log.error("Error working with statements while loading user info");
            throw new DaoException("Error when trying to create a statement user find query", e);
        }
        return userData;
    }

    @Override
    public List<RegistrationUserData> find(Criteria criteria) throws DaoException {
        PreparedStatement preparedStatement;
        ResultSet resultSet;

        Map<String, Object> criteriaMap = criteria.getCriteria();
        List<RegistrationUserData> usersData = null;

        try {
            connection = connectToDataBase();

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

            resultSet.close();
            preparedStatement.close();
            connection.close();

        } catch (SQLException e) {
            log.error("Error working with statements while find usersData by criteria");
            throw new DaoException("Error when trying to create a statement user find query", e);
        }

        return usersData;
    }

    @Override
    public List<RegistrationUserData> getUsers() throws DaoException {
        PreparedStatement preparedStatement;
        ResultSet resultSet;

        List<RegistrationUserData> usersData = null;

        try {
            connection = connectToDataBase();
            preparedStatement = connection.prepareStatement(GET_USERS_DETAILS);
            resultSet = preparedStatement.executeQuery();

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

            resultSet.close();
            preparedStatement.close();
            connection.close();

        } catch (SQLException e) {
            log.error("Error working with statements while find usersData by criteria");
            throw new DaoException("Error when trying to create a statement user find query", e);
        }

        return usersData;
    }

    @Override
    public boolean updateUserData(RegistrationUserData newUserData, String newPassword) throws DaoException {
        PreparedStatement preparedStatementForUser;
        PreparedStatement preparedStatementForUserInfo;

        try {
            connection = connectToDataBase();
            connection.setAutoCommit(false);

            if (newPassword != "") {
                preparedStatementForUser = connection.prepareStatement(UPDATE_USER_PASSWORD);
                preparedStatementForUser.setString(1, newPassword);
                preparedStatementForUser.setInt(2, newUserData.getIdUser());
                preparedStatementForUser.executeUpdate();

                preparedStatementForUser.close();
            }

            preparedStatementForUserInfo = connection.prepareStatement(UPDATE_USER_DETAILS);
            preparedStatementForUserInfo.setString(1, newUserData.getName());
            preparedStatementForUserInfo.setString(2, newUserData.getSurname());
            preparedStatementForUserInfo.setString(3, newUserData.getLastName());
            preparedStatementForUserInfo.setString(4, newUserData.getTelephoneNumber());
            preparedStatementForUserInfo.setString(5, newUserData.getEmail());
            preparedStatementForUserInfo.setString(6, newUserData.getAddress());
            preparedStatementForUserInfo.setInt(7, newUserData.getIdUser());
            preparedStatementForUserInfo.executeUpdate();

            connection.commit();
            preparedStatementForUserInfo.close();
            connection.close();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                log.error("Error rollback while update user info", ex);
                throw new DaoException("Error rollback while update user info", ex);
            }
            log.error("Error working with statements while update user info", e);
            throw new DaoException("Error while update user info", e);
        }
        return true;
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

    private Connection connectToDataBase() throws DaoException {
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
