package by.gaponenko.restaurant.dao;

import by.gaponenko.restaurant.dao.impl.SQLUserDao;

public class DaoProvider {
    private static final DaoProvider instance = new DaoProvider();

    private final UserDao userDao = new SQLUserDao();

    public static DaoProvider getInstance(){
        return instance;
    }

    public UserDao getUserDao(){
        return userDao;
    }
}
