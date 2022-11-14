package by.gaponenko.restaurant.dao;

import by.gaponenko.restaurant.bean.Bill;
import by.gaponenko.restaurant.bean.PaymentMethod;
import by.gaponenko.restaurant.dao.pool.ConnectionPool;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface PaymentDao {

    List<PaymentMethod> getPaymentMethods() throws DaoException;

    Bill createBill(int orderIdForBill) throws DaoException;

}
