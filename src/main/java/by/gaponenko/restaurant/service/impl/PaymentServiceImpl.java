package by.gaponenko.restaurant.service.impl;

import by.gaponenko.restaurant.bean.PaymentMethod;
import by.gaponenko.restaurant.dao.DaoException;
import by.gaponenko.restaurant.dao.DaoProvider;
import by.gaponenko.restaurant.dao.PaymentDao;
import by.gaponenko.restaurant.dao.UserDao;
import by.gaponenko.restaurant.service.PaymentService;
import by.gaponenko.restaurant.service.ServiceException;

import java.util.List;

public class PaymentServiceImpl implements PaymentService {

    private static final PaymentDao paymentDao = DaoProvider.getInstance().getPaymentDao();

    @Override
    public List<PaymentMethod> getPaymentMethods() throws ServiceException {
        try {
            return paymentDao.getPaymentMethods();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
