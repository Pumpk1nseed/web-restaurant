package by.gaponenko.restaurant.service;

import by.gaponenko.restaurant.bean.Order;

public interface OrderService {

    int createOrder(Order order, String userLogin) throws ServiceException;

}
