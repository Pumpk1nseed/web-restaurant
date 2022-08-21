package by.gaponenko.restaurant.dao.impl;

import by.gaponenko.restaurant.bean.RegistrationUserData;
import by.gaponenko.restaurant.bean.User;
import by.gaponenko.restaurant.dao.DaoException;
import by.gaponenko.restaurant.dao.UserDao;
import by.gaponenko.restaurant.dao.pool.ConnectionPool;
import by.gaponenko.restaurant.dao.pool.ConnectionPoolException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//через pool обратится к базе данных и создаст объект User
public class SQLUserDao implements UserDao {

        private static final String LOGIN =
                "SELECT * FROM users WHERE login = ? AND password = ?;";

    @Override
    public User authorization(String login, String password) throws DaoException {

      /*  try (Connection connection = connectToDataBase()) {
            PreparedStatement preparedStatement;
            ResultSet resultSet;

            preparedStatement = connection.prepareStatement(LOGIN);
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()) {
                //log.info("Attempt to log in with incorrect data. Login :{}", login);
                throw new DaoException("Wrong login or password");
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            //log.error("Error working with statements while sign in", e);
            throw new DaoException("Error while working with database", e);
        }*/

        User user = new User();
        user.setName("Vasya");
        user.setRole("user");

        return user;
    }

    @Override
    public boolean registration(RegistrationUserData userData) throws DaoException {
        return true;
    }

    private Connection connectToDataBase() throws DaoException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection;
        try {
            connection = connectionPool.takeConnection();
        } catch (ConnectionPoolException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }
}
