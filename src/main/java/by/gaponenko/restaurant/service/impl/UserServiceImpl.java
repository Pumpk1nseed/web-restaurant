package by.gaponenko.restaurant.service.impl;

import by.gaponenko.restaurant.bean.RegistrationUserData;
import by.gaponenko.restaurant.bean.User;
import by.gaponenko.restaurant.bean.criteria.Criteria;
import by.gaponenko.restaurant.dao.DaoException;
import by.gaponenko.restaurant.dao.DaoProvider;
import by.gaponenko.restaurant.dao.UserDao;
import by.gaponenko.restaurant.service.ServiceException;
import by.gaponenko.restaurant.service.UserService;
import by.gaponenko.restaurant.service.Validation.UserDataValidator;
import by.gaponenko.restaurant.service.Validation.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.List;

public class UserServiceImpl implements UserService {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private static final UserDataValidator validator = UserDataValidator.getInstance();
    private static final UserDao userDao = DaoProvider.getInstance().getUserDao();
    @Override
    public User authorization(String login, String password) throws ServiceException {
        if (login == "" || password == "") {
            log.info("login or password is null");
            throw new ValidationException("login or password is empty");
        }
        User user;
        try{
            user = userDao.authorization(login, password);
        } catch(DaoException e){
            log.error("Error while logging. Login: {}, Password: {}", login, password, e);
            throw new ServiceException(e.getMessage());
        }
        return user;
    }


    @Override
    public boolean registration(RegistrationUserData userData) throws ServiceException {

        if (userData.getLogin() == "" || userData.getSurname() == "" || userData.getName() == ""
                || userData.getTelephoneNumber() == "" || userData.getPassword() == "" || userData.getRole() == ""){
            log.info("Smth in user data is null");
            throw new ValidationException("All fields marked with * must be filled in");
        }

        validator.validateUserData(userData);

        boolean registered = false;
        try{
            registered = userDao.registration(userData);
            if(!registered){
                throw new ValidationException("User is already exists!");
            }
        } catch(DaoException e){
            log.error("Error while registering new user");
            throw new ServiceException(e);
        }
        return registered;
    }

    public RegistrationUserData loadUserDataByLogin(String login) throws ServiceException{
        try {
            return userDao.loadUserDataByLogin(login);
        } catch (DaoException e) {
            log.error("Error while load user data");
            throw new ServiceException(e);
        }
    }

    public List<RegistrationUserData> find(Criteria criteria) throws ServiceException {
        validator.validate(criteria);

        try {
            return userDao.find(criteria);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<RegistrationUserData> getUsers() throws ServiceException {
        try {
            return userDao.getUsers();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean updateUserData(RegistrationUserData newUserData, String newPassword) throws ServiceException {
        if (newUserData.getSurname() == null || newUserData.getName() == null
                || newUserData.getTelephoneNumber() == null){
            log.info("Smth in user data is null");
            throw new ValidationException("All fields marked with * must be filled in");
        }

        validator.validateUserDataUpdate(newUserData);

        boolean updated = false;
        try {
            updated = userDao.updateUserData(newUserData, newPassword);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return updated;
    }

}
