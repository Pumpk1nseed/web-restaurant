package by.gaponenko.restaurant.dao.impl;

import by.gaponenko.restaurant.bean.Order;
import by.gaponenko.restaurant.dao.DaoException;
import by.gaponenko.restaurant.dao.OrderDao;
import by.gaponenko.restaurant.dao.pool.ConnectionPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.Timestamp;

public class SQLOrderDao implements OrderDao {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private static final String ADD_NEW_ORDER = "INSERT INTO orders (date, id_user, status) VALUES (?, ?, ?)";
    private static final String ADD_ORDER_DETAILS = "INSERT INTO order_details (id_order, id_dish, quantity) VALUES (?, ?, ?)";
    private static final String GET_ORDERS = "SELECT ord.id_order, SUM(ordd.quantity * m.price) as 'total',  ord.date, ord.status\n" +
            "FROM orders ord LEFT JOIN order_details ordd on ordd.id_order = ord.id_order \n" +
            "LEFT JOIN menu m on ordd.id_dish = m.id_dish \n" +
            "where id_user=? group by ord.id_order;";
    private static final String IN_PROCESSING = "in processing";
    private static final int GENERATED_KEYS = 1;
    private Connection connection;

    @Override
    public int createOrder(Order order, int idUser) throws DaoException {
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        int idOrder;

        try {
            connection = connectToDataBase();

            preparedStatement = connection.prepareStatement(ADD_NEW_ORDER, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
            preparedStatement.setInt(2, idUser);
            preparedStatement.setString(3, IN_PROCESSING);
            preparedStatement.executeUpdate();

            resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();

            idOrder = resultSet.getInt(GENERATED_KEYS);

            resultSet.close();
            preparedStatement.close();
            connection.close();

        } catch (DaoException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            log.error("Error working with statements while create order", e);
            throw new DaoException("Error while working with database while create order", e);
        }
        return idOrder;
    }

    @Override
    public boolean createOrderDetails(int idOrder, int idDish, Integer quantity) throws DaoException {
        PreparedStatement preparedStatement;

        try {
            connection = connectToDataBase();

            preparedStatement = connection.prepareStatement(ADD_ORDER_DETAILS);
            preparedStatement.setInt(1, idOrder);
            preparedStatement.setInt(2, idDish);
            preparedStatement.setInt(3, quantity);
            preparedStatement.executeUpdate();

            preparedStatement.close();
            connection.close();

        } catch (SQLException e) {
            log.error("Error occurred while create order details", e);
            throw new DaoException("Error while working with database while create order details", e);
        }
        return true;
    }

    @Override
    public List<Order> getOrdersHistory(int idUser) throws DaoException {
        PreparedStatement preparedStatement;
        ResultSet resultSet;

        List<Order> orders;

        try {
            connection = connectToDataBase();

            preparedStatement = connection.prepareStatement(GET_ORDERS);
            preparedStatement.setInt(1, idUser);
            resultSet = preparedStatement.executeQuery();

            orders = new ArrayList<>();
            while (resultSet.next()) {
                Order order = new Order();
                order.setIdOrder(resultSet.getInt(1));
                order.setPrice(resultSet.getBigDecimal(2));
                order.setDateTime(resultSet.getTimestamp(3));
                order.setStatus(resultSet.getString(4));
                orders.add(order);
            }

        } catch (SQLException e) {
            log.error("Error occurred while create order details", e);
            throw new DaoException("Error while working with database while create order details", e);
        }
        return orders;
    }

    private Connection connectToDataBase() throws DaoException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        try {
            connection = connectionPool.takeConnection();
        } catch (InterruptedException e) {
            log.error("Error while getting connection from connection pool queue", e);
            throw new DaoException("Error taking connection to database", e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }
}
