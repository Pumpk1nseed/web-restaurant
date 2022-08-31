package by.gaponenko.restaurant.dao.impl;

import by.gaponenko.restaurant.bean.Order;
import by.gaponenko.restaurant.dao.DaoException;
import by.gaponenko.restaurant.dao.OrderDao;
import by.gaponenko.restaurant.dao.pool.ConnectionPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

public class SQLOrderDao implements OrderDao{
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private static final String ADD_NEW_ORDER = "INSERT INTO orders (date, id_user, status) VALUES (?, ?, ?)";
    private static final String ADD_ORDER_DETAILS ="INSERT INTO order_details (id_order, id_dish, quantity) VALUES (?, ?, ?)";
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
            preparedStatement.setString(2, IN_PROCESSING);
            preparedStatement.setInt(3, idUser);
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
    public boolean createOrderDetails(int idOrder, int idDish, Integer quantity) throws DaoException, SQLException {
        PreparedStatement preparedStatement;
        ResultSet resultSet;

        preparedStatement = connection.prepareStatement(ADD_ORDER_DETAILS);
        preparedStatement.setInt(1, idOrder);
        preparedStatement.setInt(2, idDish);
        preparedStatement.setInt(3,quantity);
        preparedStatement.executeUpdate();

        try {
            connection = connectToDataBase();
        } catch (DaoException e) {
            log.error("Error occurred while create order details", e);
            throw new DaoException("Error while working with database while create order details", e);
        }
        return true;
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
