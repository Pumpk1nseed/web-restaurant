package by.gaponenko.restaurant.dao;

import by.gaponenko.restaurant.bean.Order;

import java.sql.SQLException;
import java.util.List;

public interface OrderDao {
    int createOrder(Order order, int idUser) throws DaoException;

    boolean createOrderDetails(int idOrder, int idDish, Integer quantity) throws DaoException;

    List<Order> getOrdersHistory(int idUser) throws DaoException;

}
