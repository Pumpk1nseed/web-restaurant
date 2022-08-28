package by.gaponenko.restaurant.dao;

import by.gaponenko.restaurant.dao.impl.SQLMenuDao;
import by.gaponenko.restaurant.dao.impl.SQLUserDao;

public class DaoProvider {
    private static final DaoProvider instance = new DaoProvider();
    private final UserDao userDao = new SQLUserDao();
    private final MenuDao menuDao = new SQLMenuDao();

    public static DaoProvider getInstance(){
        return instance;
    }

    public UserDao getUserDao(){
        return userDao;
    }
    public MenuDao getMenuDao(){
        return menuDao;
    }
}
