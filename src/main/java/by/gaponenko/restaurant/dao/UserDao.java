package by.gaponenko.restaurant.dao;

import by.gaponenko.restaurant.bean.RegistrationUserData;
import by.gaponenko.restaurant.bean.User;

public interface UserDao {

    User authorization(String login, String password) throws DaoException, ClassNotFoundException;

    boolean registration(RegistrationUserData userData) throws DaoException;

}
