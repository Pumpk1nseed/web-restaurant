package by.gaponenko.restaurant.service;

import by.gaponenko.restaurant.bean.PaymentMethod;

import java.util.List;

public interface PaymentService {

    List<PaymentMethod> getPaymentMethods() throws ServiceException;

    int createBill(int orderIdForBill) throws ServiceException;

    boolean updateBillStatus(int idBill, String status) throws ServiceException;
}
