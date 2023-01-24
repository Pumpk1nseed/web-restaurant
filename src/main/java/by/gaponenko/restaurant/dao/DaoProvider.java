package by.gaponenko.restaurant.dao;

import by.gaponenko.restaurant.dao.impl.SQLMenuDao;
import by.gaponenko.restaurant.dao.impl.SQLOrderDao;
import by.gaponenko.restaurant.dao.impl.SQLPaymentDao;
import by.gaponenko.restaurant.dao.impl.SQLUserDao;
import by.gaponenko.restaurant.dao.util.TransactionDao;
import by.gaponenko.restaurant.dao.util.TransactionImpl;

public class DaoProvider {
    private static final DaoProvider instance = new DaoProvider();
    private final UserDao userDao = new SQLUserDao();
    private final MenuDao menuDao = new SQLMenuDao();
    private final OrderDao orderDao = new SQLOrderDao();
    private final PaymentDao paymentDao = new SQLPaymentDao();
    private final TransactionDao transactionDao = new TransactionImpl();

    public static DaoProvider getInstance() {
        return instance;
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public MenuDao getMenuDao() {
        return menuDao;
    }

    public OrderDao getOrderDao() {
        return orderDao;
    }
    public PaymentDao getPaymentDao() {
        return paymentDao;
    }

    public TransactionDao getTransactionDao() {return transactionDao; };
}
