package by.gaponenko.restaurant.dao;

import by.gaponenko.restaurant.bean.Order;
import by.gaponenko.restaurant.bean.RegistrationUserData;
import by.gaponenko.restaurant.bean.criteria.Criteria;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface OrderDao {
    int createOrder(Order order, int idUser) throws DaoException;

    boolean createOrderDetails(int idOrder, int idDish, Integer quantity, int idPaymentMethod) throws DaoException;

    List<Order> getOrdersHistory(int idUser, int idRole) throws DaoException;

    Map<Order, RegistrationUserData> findOrdersByUsersInfo(Criteria criteria) throws DaoException;

    boolean updateOrderStatus(int idOrder, String status) throws DaoException;

}
