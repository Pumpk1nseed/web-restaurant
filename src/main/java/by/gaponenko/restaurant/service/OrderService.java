package by.gaponenko.restaurant.service;

import by.gaponenko.restaurant.bean.Order;
import by.gaponenko.restaurant.bean.OrderForCooking;
import by.gaponenko.restaurant.bean.RegistrationUserData;
import by.gaponenko.restaurant.bean.criteria.Criteria;
import by.gaponenko.restaurant.dao.DaoException;

import java.util.List;
import java.util.Map;

public interface OrderService {

    int createOrder(Order order, int idUser) throws ServiceException;

    boolean createOrderDetails(int idOrder, int idDish, Integer quantity, int idPaymentMethod) throws ServiceException;

    List<Order> getOrdersHistory (int idUser) throws ServiceException;

    Map<Order, RegistrationUserData> getOrdersHistory() throws ServiceException;

    Map<Order, RegistrationUserData> findOrderByUsersInfo(Criteria criteria) throws ServiceException;

    boolean updateOrderStatus(int idOrder, String status) throws ServiceException;

    List<OrderForCooking> findOrderByDishInfo(Criteria criteria) throws ServiceException;

}
