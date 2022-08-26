package by.gaponenko.restaurant.service.impl;

import by.gaponenko.restaurant.bean.RegistrationUserData;
import by.gaponenko.restaurant.bean.User;
import by.gaponenko.restaurant.dao.DaoException;
import by.gaponenko.restaurant.dao.DaoProvider;
import by.gaponenko.restaurant.dao.UserDao;
import by.gaponenko.restaurant.service.ServiceException;
import by.gaponenko.restaurant.service.UserService;
import by.gaponenko.restaurant.service.Validation.UserDataValidator;
import by.gaponenko.restaurant.service.Validation.ValidationException;

import java.sql.SQLException;

public class UserServiceImpl implements UserService {

    private static final UserDataValidator validator = UserDataValidator.getInstance();

    private static final UserDao userDao = DaoProvider.getInstance().getUserDao();
    @Override
    public User authorization(String login, String password) throws ServiceException {
        if (!validator.validate(login, password)) {
            throw new ServiceException("login or password is incorrect!");
        }

        User user;
        try{
            user = userDao.authorization(login, password);
        } catch(DaoException e){
            throw new ServiceException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return user;
    }


    @Override
    public boolean registration(RegistrationUserData userData) throws ServiceException {
        if (!validator.validateUserData(userData)){
            throw new ServiceException("Check your data!");
        }

        boolean registred = false;
        try{
            registred = userDao.registration(userData);
            if(!registred){
                throw new ValidationException("User is already exists!");
            }
        } catch(DaoException e){
            throw new ServiceException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return registred;
    }
}
