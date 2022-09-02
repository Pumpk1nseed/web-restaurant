package by.gaponenko.restaurant.service.impl;

import by.gaponenko.restaurant.bean.Order;
import by.gaponenko.restaurant.bean.RegistrationUserData;
import by.gaponenko.restaurant.bean.criteria.Criteria;
import by.gaponenko.restaurant.bean.criteria.SearchCriteria;
import by.gaponenko.restaurant.dao.DaoException;
import by.gaponenko.restaurant.dao.DaoProvider;
import by.gaponenko.restaurant.dao.OrderDao;
import by.gaponenko.restaurant.dao.UserDao;
import by.gaponenko.restaurant.service.OrderService;
import by.gaponenko.restaurant.service.ServiceException;
import by.gaponenko.restaurant.service.Validation.OrderValidator;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class OrderServiceImpl implements OrderService {
    private static final OrderValidator validator = OrderValidator.getInstance();
    private static final OrderDao orderDao = DaoProvider.getInstance().getOrderDao();
    private static final UserDao userDao = DaoProvider.getInstance().getUserDao();

    @Override
    public int createOrder(Order order, Integer idUser) throws ServiceException {
        validator.validate(order);

        Criteria criteria = new Criteria();
        criteria.add(SearchCriteria.User.ID_USER.toString(), idUser);

        try {
            List<RegistrationUserData> users = userDao.find(criteria);

            int orderId = orderDao.createOrder(order, users.get(0).getIdUser());

            return orderId;

        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean createOrderDetails(int idOrder, int idDish, Integer quantity, int idPaymentMethod) throws ServiceException {
        validator.validate(idDish, idDish, quantity);

        try {
            return orderDao.createOrderDetails(idOrder, idDish, quantity, idPaymentMethod);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Order> getOrdersHistory(int idUser) throws ServiceException {
        validator.validate(idUser);

        try {
            return orderDao.getOrdersHistory(idUser);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Map<Order, RegistrationUserData> findOrderByUsersInfo(Criteria criteria) throws ServiceException {
        validator.validate(criteria);

        try {
            return orderDao.findOrdersByUsersInfo(criteria);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean updateOrderStatus(int idOrder, String status) throws ServiceException {
        try {
            return orderDao.updateOrderStatus(idOrder, status);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}