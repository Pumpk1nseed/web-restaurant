package by.gaponenko.restaurant.dao.impl;

import by.gaponenko.restaurant.bean.Order;
import by.gaponenko.restaurant.bean.RegistrationUserData;
import by.gaponenko.restaurant.bean.criteria.Criteria;
import by.gaponenko.restaurant.dao.DaoException;
import by.gaponenko.restaurant.dao.OrderDao;
import by.gaponenko.restaurant.dao.pool.ConnectionPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.sql.Timestamp;
import java.util.Map;

public class SQLOrderDao implements OrderDao {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private static final String ADD_NEW_ORDER = "INSERT INTO orders (date, id_user, status) VALUES (?, ?, ?)";
    private static final String ADD_ORDER_DETAILS = "INSERT INTO order_details (id_order, id_dish, quantity, id_payment_method) VALUES (?, ?, ?, ?)";
    private static final String GET_ORDERS = "SELECT ord.id_order, SUM(ordd.quantity * m.price) as 'total',  ord.date, ord.status\n" +
            "FROM orders ord LEFT JOIN order_details ordd on ordd.id_order = ord.id_order \n" +
            "LEFT JOIN menu m on ordd.id_dish = m.id_dish \n" +
            "where id_user=? group by ord.id_order;";

    private static final String GET_ORDERS_BY_USER_INFO = "SELECT ord.id_order, SUM(ordd.quantity * m.price) as 'total',  ord.date, ord.status, ud.name, ud.surname, ud.address, ud.telephone_number\n" +
            "            FROM orders ord LEFT JOIN order_details ordd on ordd.id_order = ord.id_order\n" +
            "            LEFT JOIN users_details ud on ord.id_user = ud.id_user\n" +
            "            LEFT JOIN menu m on ordd.id_dish = m.id_dish\n" +
            "            where %s group by ord.id_order;";

    private static final String UPDATE_ORDER_STATUS = "UPDATE orders SET status=? where id_order=?;";
    private static final String IN_PROCESSING = "in processing";
    private static final int GENERATED_KEYS = 1;
    private static final String AND = "AND ";
    private Connection connection;

    @Override
    public int createOrder(Order order, int idUser) throws DaoException {
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        int idOrder;

        try {
            connection = connectToDataBase();
            connection.setAutoCommit(false);

            preparedStatement = connection.prepareStatement(ADD_NEW_ORDER, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
            preparedStatement.setInt(2, idUser);
            preparedStatement.setString(3, IN_PROCESSING);
            preparedStatement.executeUpdate();

            resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();

            idOrder = resultSet.getInt(GENERATED_KEYS);

            connection.commit();
            resultSet.close();
            preparedStatement.close();
            connection.close();

        } catch (SQLException e) {
            log.error("Error working with statements while create order", e);
            throw new DaoException("Error while working with database while create order", e);
        }
        return idOrder;
    }

    @Override
    public boolean createOrderDetails(int idOrder, int idDish, Integer quantity, int idPaymentMethod) throws DaoException {
        PreparedStatement preparedStatement;

        try {
            connection = connectToDataBase();
            connection.setAutoCommit(false);

            preparedStatement = connection.prepareStatement(ADD_ORDER_DETAILS);
            preparedStatement.setInt(1, idOrder);
            preparedStatement.setInt(2, idDish);
            preparedStatement.setInt(3, quantity);
            preparedStatement.setInt(4, idPaymentMethod);
            preparedStatement.executeUpdate();

            connection.commit();
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

            resultSet.close();
            preparedStatement.close();
            connection.close();

        } catch (SQLException e) {
            log.error("Error occurred while get orders history", e);
            throw new DaoException("Error while working with database while get orders history", e);
        }
        return orders;
    }

    @Override
    public Map<Order, RegistrationUserData> findOrdersByUsersInfo(Criteria criteria) throws DaoException {
        PreparedStatement preparedStatement;
        ResultSet resultSet;

        Map<Order, RegistrationUserData> orderUsedDataMap;

        try {
            connection = connectToDataBase();

            Map<String, Object> criterias = criteria.getCriteria();

            StringBuilder sqlBuilder = new StringBuilder("");
            for (String criteriaName : criterias.keySet()) {
                sqlBuilder.append(String.format("%s=? %s", criteriaName.toLowerCase(), AND));
            }
            sqlBuilder = new StringBuilder(sqlBuilder.substring(0, sqlBuilder.length() - AND.length()));
            String queryBuilder = String.format(GET_ORDERS_BY_USER_INFO, sqlBuilder);

            preparedStatement = connection.prepareStatement(queryBuilder);
            int i = 1;
            for (Object value : criterias.values()) {
                preparedStatement.setString(1, value.toString());
                i++;
            }
            resultSet = preparedStatement.executeQuery();

            orderUsedDataMap = new LinkedHashMap<>();
            while (resultSet.next()) {
                Order order = new Order();
                order.setIdOrder(resultSet.getInt(1));
                order.setPrice(resultSet.getBigDecimal(2));
                order.setDateTime(resultSet.getTimestamp(3));
                order.setStatus(resultSet.getString(4));

                RegistrationUserData userData = new RegistrationUserData();
                userData.setName(resultSet.getString(5));
                userData.setSurname(resultSet.getString(6));
                userData.setAddress(resultSet.getString(7));
                userData.setTelephoneNumber(resultSet.getString(8));

                orderUsedDataMap.put(order, userData);
            }

            resultSet.close();
            preparedStatement.close();
            connection.close();

        } catch (SQLException e) {
            log.error("Error occurred while find orders by criteria", e);
            throw new DaoException("Error while working with database while find orders by users info", e);
        }
        return orderUsedDataMap;
    }

    @Override
    public boolean updateOrderStatus(int idOrder, String status) throws DaoException {
        PreparedStatement preparedStatement;

        try {
            connection = connectToDataBase();

            preparedStatement = connection.prepareStatement(UPDATE_ORDER_STATUS);
            preparedStatement.setString(1, status);
            preparedStatement.setInt(2, idOrder);
            preparedStatement.executeUpdate();

            preparedStatement.close();
            connection.close();

            return true;

        } catch (SQLException e) {
            log.error("Error occurred while updating order status", e);
            throw new DaoException("Error while working with database while updating order status", e);
        }
    }

    private Connection connectToDataBase() throws DaoException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        try {
            connection = connectionPool.takeConnection();
        } catch (InterruptedException | SQLException | ClassNotFoundException e) {
            log.error("Error while getting connection from connection pool queue", e);
            throw new DaoException("Error taking connection to database", e);
        }
        return connection;
    }
}
