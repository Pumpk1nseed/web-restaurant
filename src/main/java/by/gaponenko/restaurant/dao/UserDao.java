package by.gaponenko.restaurant.dao;

import by.gaponenko.restaurant.bean.RegistrationUserData;
import by.gaponenko.restaurant.bean.User;

import java.sql.SQLException;

public interface UserDao {

    User authorization(String login, String password) throws DaoException, ClassNotFoundException, SQLException, InterruptedException;

    boolean registration(RegistrationUserData userData) throws DaoException, SQLException;

}
