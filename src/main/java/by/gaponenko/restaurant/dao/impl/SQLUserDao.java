package by.gaponenko.restaurant.dao.impl;

import by.gaponenko.restaurant.bean.RegistrationUserData;
import by.gaponenko.restaurant.bean.User;
import by.gaponenko.restaurant.dao.DaoException;
import by.gaponenko.restaurant.dao.UserDao;
import by.gaponenko.restaurant.dao.pool.ConnectionPool;
import by.gaponenko.restaurant.dao.pool.ConnectionPoolException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;

import java.sql.*;


//через pool обратится к базе данных и создаст объект User
public class SQLUserDao implements UserDao {

    private Connection connection;
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private static final String LOGIN =
            "SELECT * FROM users WHERE login = ? AND password = ?;";

    @Override
    public User authorization(String login, String password) throws DaoException, ClassNotFoundException {

       /* try {
            connection = connectToDataBase();

            String sql = "INSERT INTO users(idusers, name, surname, role) VALUES(?,?,?,?)";
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setString(1, "1");
            ps.setString(2, "Natasha");
            ps.setString(3, "Ilyinets");
            ps.setString(4, "test2");

            ps.executeUpdate();

            System.out.println("!!!");

            PreparedStatement preparedStatement;
            ResultSet resultSet;

            preparedStatement = connection.prepareStatement(LOGIN);
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()) {
                log.info("Attempt to log in with incorrect data. Login :{}", login);
                throw new DaoException("Wrong login or password");
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            log.error("Error working with statements while sign in", e);
            throw new DaoException("Error while working with database", e);
        }
*/
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
        try {
            connection = connectionPool.takeConnection();
        } catch (InterruptedException e) {
            log.error("Error while getting connection from connection pool queue", e);
            throw new DaoException("Error taking connection to database", e);
        }
        return connection;
    }
}
