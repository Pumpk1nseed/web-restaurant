package by.gaponenko.restaurant.service.impl;

import by.gaponenko.restaurant.bean.User;
import by.gaponenko.restaurant.dao.DaoException;
import by.gaponenko.restaurant.dao.DaoProvider;
import by.gaponenko.restaurant.dao.UserDao;
import by.gaponenko.restaurant.service.ServiceException;
import by.gaponenko.restaurant.service.UserService;
import by.gaponenko.restaurant.service.Validation.UserDataValidator;

public class UserServiceImpl implements UserService {

    private static final UserDataValidator validator = UserDataValidator.getInstance();

    @Override
    public User authorization(String login, String password) throws ServiceException {
        if (!validator.check(login, password)) {
            throw new ServiceException("login or password is incorrect!");
        }

        UserDao userDao = DaoProvider.getInstance().getUserDao();

        User user;
        try{
            user = userDao.authorization(login, password);
        } catch(DaoException e){
            throw new ServiceException(e);
        }
        return user;
    }
}
