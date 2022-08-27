package by.gaponenko.restaurant.dao.impl;

import by.gaponenko.restaurant.bean.RegistrationUserData;
import by.gaponenko.restaurant.bean.User;
import by.gaponenko.restaurant.dao.DaoException;
import by.gaponenko.restaurant.dao.UserDao;
import by.gaponenko.restaurant.dao.pool.ConnectionPool;
import by.gaponenko.restaurant.service.ServiceException;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;

import java.sql.*;

public class SQLUserDao implements UserDao {
    // private final Logger log = LoggerFactory.getLogger(this.getClass());
    private static final String STATUS = "status";

    private static final String FIND_AUTHORIZED_USER = "SELECT * FROM users WHERE login = ? AND password = ?;";
    private static final String FIND_LOGIN = "SELECT * FROM users WHERE login = ?;";
    private static final String ADD_NEW_USER = "INSERT INTO users (login, password, id_role, status) VALUES(?,?,?,?);";
    private static final String REGISTER_USER_INFO = "INSERT INTO users_details(id_user, name, surname, last_name, date_of_birth, telephone_number, email, address) VALUES(?,?,?,?,?,?,?,?)";

    private Connection connection;

    @Override
    public User authorization(String login, String pass) throws DaoException {

        User user = new User();

        try {
            connection = connectToDataBase();
            PreparedStatement preparedStatement;
            ResultSet resultSet;

            preparedStatement = connection.prepareStatement(FIND_AUTHORIZED_USER);
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, pass);
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()) {
                //log.info("Attempt to log in with incorrect data. Login :{}", login);
                throw new DaoException("Wrong login or password");
            }

            if (resultSet.getString(STATUS) == "inactive") {
                //log.info("Attempt to log in from deleted user. Login :{}", login);
                throw new DaoException("Sorry, your profile was deleted.");
            }

            user.setLogin(resultSet.getString(2));
            user.setPassword(resultSet.getString(3));

            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            //log.error("Error working with statements while sign in", e);
            throw new DaoException("Error while working with database", e);
        }
        return user;
    }

    @Override
    public boolean registration(RegistrationUserData userData) throws DaoException, SQLException {
        try {
            connection = connectToDataBase();
            PreparedStatement preparedStatementForUser;
            PreparedStatement preparedStatementForUserInfo;
            ResultSet resultSet;

            // вводим роли
/*            PreparedStatement preparedStatement1;
            preparedStatement1 = connection.prepareStatement("INSERT INTO roles (title) VALUES(?);");
            preparedStatement1.setString(1, "user");
            preparedStatement1.executeUpdate();
            preparedStatement1.setString(1, "admin");
            preparedStatement1.executeUpdate();*/

            resultSet = findLogin(connection, userData);

            // проверяем, есть ли пользователь с таким логином
            if (resultSet.next()) {
                return false;
            }

            preparedStatementForUser = connection.prepareStatement(ADD_NEW_USER);
            preparedStatementForUser.setString(1, userData.getLogin());
            preparedStatementForUser.setString(2, userData.getPassword());
            preparedStatementForUser.setString(3, userData.getRole());
            preparedStatementForUser.setString(4, "active");
            preparedStatementForUser.executeUpdate();

            resultSet = findLogin(connection, userData);
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

            resultSet.close();
            preparedStatementForUser.close();
            preparedStatementForUserInfo.close();
            connection.close();
        } catch (SQLException e) {
            //log.error("Error working with statements while sign in", e);
            throw new DaoException("Error while adding new User", e);
        }
        return true;
    }

    public RegistrationUserData editUserInfo (RegistrationUserData userData){
        return userData;
    }

    private Connection connectToDataBase() throws DaoException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        try {
            connection = connectionPool.takeConnection();
        } catch (InterruptedException e) {
            //   log.error("Error while getting connection from connection pool queue", e);
            throw new DaoException("Error taking connection to database", e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }

    private ResultSet findLogin(Connection connection, RegistrationUserData userData) throws SQLException {
        PreparedStatement preparedStatement;
        ResultSet resultSet;

        preparedStatement = connection.prepareStatement(FIND_LOGIN);
        preparedStatement.setString(1, userData.getLogin());
        resultSet = preparedStatement.executeQuery();

        return resultSet;
    }
}
