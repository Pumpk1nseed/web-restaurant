package by.gaponenko.restaurant.service;

import by.gaponenko.restaurant.bean.Order;
import by.gaponenko.restaurant.dao.DaoException;

import java.util.List;

public interface OrderService {

    int createOrder(Order order, Integer idUser) throws ServiceException;

    boolean createOrderDetails(int idOrder, int idDish, Integer quantity) throws ServiceException, DaoException;

    List<Order> getOrdersHistory (int idUser) throws ServiceException;

}
