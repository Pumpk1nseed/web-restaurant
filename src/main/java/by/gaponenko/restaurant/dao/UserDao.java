package by.gaponenko.restaurant.dao;

import by.gaponenko.restaurant.bean.Dish;
import by.gaponenko.restaurant.bean.RegistrationUserData;
import by.gaponenko.restaurant.bean.User;
import by.gaponenko.restaurant.bean.criteria.Criteria;

import java.sql.SQLException;
import java.util.List;

public interface UserDao {

    User authorization(String login, String password) throws DaoException;

    boolean registration(RegistrationUserData userData) throws DaoException;

    RegistrationUserData loadUserDataByLogin(String login) throws DaoException;

    List<RegistrationUserData> find(Criteria criteria) throws DaoException;

}
