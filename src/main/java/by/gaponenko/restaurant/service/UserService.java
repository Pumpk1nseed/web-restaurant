package by.gaponenko.restaurant.service;

import by.gaponenko.restaurant.bean.RegistrationUserData;
import by.gaponenko.restaurant.bean.User;

public interface UserService {

    User authorization(String login, String password) throws ServiceException;

    boolean registration(RegistrationUserData usrData) throws ServiceException;
}
