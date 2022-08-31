package by.gaponenko.restaurant.dao;

import by.gaponenko.restaurant.bean.Order;

import java.sql.SQLException;

public interface OrderDao {
    int createOrder(Order order, int idUser) throws DaoException;

    boolean createOrderDetails(int idOrder, int idDish, Integer quantity) throws DaoException, SQLException;

}
