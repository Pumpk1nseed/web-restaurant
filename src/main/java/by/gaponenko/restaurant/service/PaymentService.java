package by.gaponenko.restaurant.service;

import by.gaponenko.restaurant.bean.Bill;
import by.gaponenko.restaurant.bean.PaymentMethod;

import java.util.List;

public interface PaymentService {

    List<PaymentMethod> getPaymentMethods() throws ServiceException;

    Bill createBill(int orderIdForBill) throws ServiceException;
}
