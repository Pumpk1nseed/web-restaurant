package by.gaponenko.restaurant.dao;

import by.gaponenko.restaurant.bean.PaymentMethod;

import java.util.List;

public interface PaymentDao {

    List<PaymentMethod> getPaymentMethods() throws DaoException;

    int createBill(int orderIdForBill) throws DaoException;

    boolean updateBillStatus(int idBill, String status) throws DaoException;

}
