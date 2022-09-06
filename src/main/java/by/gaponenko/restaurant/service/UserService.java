package by.gaponenko.restaurant.service;

import by.gaponenko.restaurant.bean.RegistrationUserData;
import by.gaponenko.restaurant.bean.User;
import by.gaponenko.restaurant.bean.criteria.Criteria;

import java.util.List;

public interface UserService {

    User authorization(String login, String password) throws ServiceException;

    boolean registration(RegistrationUserData userData) throws ServiceException;

    RegistrationUserData loadUserDataByLogin(String login) throws ServiceException;

    List<RegistrationUserData> find(Criteria criteria) throws ServiceException;
    List<RegistrationUserData> getUsers() throws ServiceException;

    boolean updateUserData(RegistrationUserData newUserData, String newPassword) throws ServiceException;
}
