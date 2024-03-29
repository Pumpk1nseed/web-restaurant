package by.gaponenko.restaurant.dao;

import by.gaponenko.restaurant.bean.Order;
import by.gaponenko.restaurant.bean.OrderForCooking;
import by.gaponenko.restaurant.bean.RegistrationUserData;
import by.gaponenko.restaurant.bean.criteria.Criteria;
import by.gaponenko.restaurant.controller.RequestParameterName;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface OrderDao {
    int createOrder(Order order, int idUser) throws DaoException;

    boolean createOrderDetails(int idOrder, int idDish, Integer quantity, int idPaymentMethod) throws DaoException;

    List<Order> getOrdersHistory(int idUser) throws DaoException;

    Map<Order, RegistrationUserData>  getOrdersHistory() throws DaoException;

    Map<Order, RegistrationUserData> findOrdersByUsersInfo(Criteria criteria) throws DaoException;

    boolean updateOrderStatus(int idOrder, String status) throws DaoException;

    List<OrderForCooking> findOrdersByDishInfo(Criteria criteria) throws DaoException;

    Map<OrderForCooking, RegistrationUserData> findOrdersUsersByDishInfo(Criteria criteria) throws DaoException;

}
