package by.gaponenko.restaurant.dao.impl;

import by.gaponenko.restaurant.bean.Dish;
import by.gaponenko.restaurant.bean.Order;
import by.gaponenko.restaurant.bean.OrderForCooking;
import by.gaponenko.restaurant.bean.RegistrationUserData;
import by.gaponenko.restaurant.bean.criteria.Criteria;
import by.gaponenko.restaurant.dao.DaoException;
import by.gaponenko.restaurant.dao.OrderDao;
import by.gaponenko.restaurant.dao.pool.ConnectionPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.*;
import java.sql.Timestamp;

public class SQLOrderDao implements OrderDao {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    ConnectionPool connectionPool = ConnectionPool.getInstance();

    private static final String ADD_NEW_ORDER = "INSERT INTO orders (date, id_user, status) VALUES (?, ?, ?)";
    private static final String ADD_ORDER_DETAILS = "INSERT INTO order_details (id_order, id_dish, quantity, id_payment_method) VALUES (?, ?, ?, ?)";
    private static final String GET_ORDERS_BY_USER_ID = "SELECT ord.id_order, SUM(ordd.quantity * m.price) as 'total',  ord.date, ord.status\n" +
            "FROM orders ord LEFT JOIN order_details ordd on ordd.id_order = ord.id_order \n" +
            "LEFT JOIN menu m on ordd.id_dish = m.id_dish \n" +
            "where id_user=? group by ord.id_order;";

    private static final String GET_ALL_ORDERS = "SELECT ord.id_order, SUM(ordd.quantity * m.price) as 'total',  ord.date, ord.status, ud.name, ud.surname, ud.address, ud.telephone_number\n" +
            "            FROM orders ord LEFT JOIN order_details ordd on ordd.id_order = ord.id_order\n" +
            "            LEFT JOIN users_details ud on ord.id_user = ud.id_user\n" +
            "            LEFT JOIN menu m on ordd.id_dish = m.id_dish\n" +
            "            group by ord.id_order;";

    private static final String GET_DISH_LIST_BY_ORDER_ID = "SELECT m.name, m.description, m.price, ordd.quantity FROM order_details ordd LEFT JOIN menu m on ordd.id_dish = m.id_dish where id_order=?;";

    private static final String GET_ORDERS_BY_USER_INFO = "SELECT ord.id_order, SUM(ordd.quantity * m.price) as 'total',  ord.date, ord.status, ud.name, ud.surname, ud.address, ud.telephone_number\n" +
            "            FROM orders ord LEFT JOIN order_details ordd on ordd.id_order = ord.id_order\n" +
            "            LEFT JOIN users_details ud on ord.id_user = ud.id_user\n" +
            "            LEFT JOIN menu m on ordd.id_dish = m.id_dish\n" +
            "            where %s group by ord.id_order;";

    private static final String GET_ORDERS_USERS_BY_DISH_INFO = "SELECT ord.id_order, m.name, pm.name, quantity, m.price, ud.name, ud.surname, ud.address, ud.telephone_number, b.status\n" +
            "            FROM orders ord\n" +
            "            LEFT JOIN order_details ordd on ordd.id_order = ord.id_order\n" +
            "            LEFT JOIN payment_methods pm on ordd.id_payment_method = pm.id_payment_methods\n" +
            "            LEFT JOIN users_details ud on ord.id_user = ud.id_user\n" +
            "            LEFT JOIN bill b on ord.id_order = b.id_order\n" +
            "            LEFT JOIN menu m on ordd.id_dish = m.id_dish where %s;";

    private static final String GET_ORDERS_BY_DISH_INFO = "SELECT ord.id_order, m.name, pm.name, quantity, m.price \n" +
            "FROM orders ord \n" +
            "LEFT JOIN order_details ordd on ordd.id_order = ord.id_order\n" +
            "LEFT JOIN payment_methods pm on ordd.id_payment_method = pm.id_payment_methods\n" +
            "LEFT JOIN menu m on ordd.id_dish = m.id_dish where %s;";

    private static final String UPDATE_ORDER_STATUS = "UPDATE orders SET status=? where id_order=?;";
    private static final String IN_PROCESSING = "in processing";
    private static final int GENERATED_KEYS = 1;
    private static final String AND = "AND ";

    @Override
    public int createOrder(Order order, int idUser) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int idOrder;

        try {
            connection = connectToDataBase(connection);
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
            return idOrder;

        } catch (SQLException e) {
            log.error("Error working with statements while create order", e);
            throw new DaoException("Error while working with database while create order", e);
        } finally {
            try {
                connectionPool.closeConnection(connection, preparedStatement, resultSet);
            } catch (SQLException e) {
                log.error("Error while close connection...", e);
            }
        }
    }

    @Override
    public boolean createOrderDetails(int idOrder, int idDish, Integer quantity, int idPaymentMethod) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectToDataBase(connection);
            connection.setAutoCommit(false);

            preparedStatement = connection.prepareStatement(ADD_ORDER_DETAILS);
            preparedStatement.setInt(1, idOrder);
            preparedStatement.setInt(2, idDish);
            preparedStatement.setInt(3, quantity);
            preparedStatement.setInt(4, idPaymentMethod);
            preparedStatement.executeUpdate();

            connection.commit();
            return true;

        } catch (SQLException e) {
            log.error("Error occurred while create order details", e);
            throw new DaoException("Error while working with database while create order details", e);
        } finally {
            try {
                connectionPool.closeConnection(connection, preparedStatement, resultSet);
            } catch (SQLException e) {
                log.error("Error while close connection...", e);
            }
        }
    }

    @Override
    public List<Order> getOrdersHistory(int idUser) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        List<Order> orders;

        try {
            connection = connectToDataBase(connection);

            preparedStatement = connection.prepareStatement(GET_ORDERS_BY_USER_ID);
            preparedStatement.setInt(1, idUser);
            resultSet = preparedStatement.executeQuery();

            orders = new ArrayList<>();
            while (resultSet.next()) {
                Order order = new Order();
                Map<Dish, Integer> orderList = new HashMap<>();
                orderList = getOrderListByOrderId(connection, resultSet.getInt(1));
                order.setIdOrder(resultSet.getInt(1));
                order.setPrice(resultSet.getBigDecimal(2));
                order.setDateTime(resultSet.getTimestamp(3));
                order.setStatus(resultSet.getString(4));
                order.setOrderList(orderList);
                orders.add(order);
            }

            return orders;

        } catch (SQLException e) {
            log.error("Error occurred while get orders history", e);
            throw new DaoException("Error while working with database while get orders history", e);
        } finally {
            try {
                connectionPool.closeConnection(connection, preparedStatement, resultSet);
            } catch (SQLException e) {
                log.error("Error while close connection...", e);
            }
        }
    }

    @Override
    public Map<Order, RegistrationUserData> getOrdersHistory() throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        Map<Order, RegistrationUserData> orderUsedDataMap;

        try {
            connection = connectToDataBase(connection);

            preparedStatement = connection.prepareStatement(GET_ALL_ORDERS);
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

            return orderUsedDataMap;

        } catch (SQLException e) {
            log.error("Error occurred while get orders history", e);
            throw new DaoException("Error while working with database while get orders history", e);
        } finally {
            try {
                connectionPool.closeConnection(connection, preparedStatement, resultSet);
            } catch (SQLException e) {
                log.error("Error while close connection...", e);
            }
        }
    }

    @Override
    public Map<Order, RegistrationUserData> findOrdersByUsersInfo(Criteria criteria) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        Map<Order, RegistrationUserData> orderUserDataMap;

        try {
            connection = connectToDataBase(connection);

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

            orderUserDataMap = new LinkedHashMap<>();
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

                orderUserDataMap.put(order, userData);
            }

            return orderUserDataMap;

        } catch (SQLException e) {
            log.error("Error occurred while find orders by criteria", e);
            throw new DaoException("Error while working with database while find orders by users info", e);
        } finally {
            try {
                connectionPool.closeConnection(connection, preparedStatement, resultSet);
            } catch (SQLException e) {
                log.error("Error while close connection...", e);
            }
        }
    }

    @Override
    public boolean updateOrderStatus(int idOrder, String status) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectToDataBase(connection);
            connection.setAutoCommit(false);

            preparedStatement = connection.prepareStatement(UPDATE_ORDER_STATUS);
            preparedStatement.setString(1, status);
            preparedStatement.setInt(2, idOrder);
            preparedStatement.executeUpdate();

            connection.commit();
            return true;

        } catch (SQLException e) {
            log.error("Error occurred while updating order status", e);
            throw new DaoException("Error while working with database while updating order status", e);
        } finally {
            try {
                connectionPool.closeConnection(connection, preparedStatement, resultSet);
            } catch (SQLException e) {
                log.error("Error while close connection...", e);
            }
        }
    }

    @Override
    public List<OrderForCooking> findOrdersByDishInfo(Criteria criteria) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectToDataBase(connection);

            Map<String, Object> criterias = criteria.getCriteria();

            StringBuilder sqlBuilder = new StringBuilder("");
            for (String criteriaName : criterias.keySet()) {
                sqlBuilder.append(String.format("%s=? %s", criteriaName.toLowerCase(), AND));
            }
            sqlBuilder = new StringBuilder(sqlBuilder.substring(0, sqlBuilder.length() - AND.length()));
            String queryBuilder = String.format(GET_ORDERS_BY_DISH_INFO, sqlBuilder);

            preparedStatement = connection.prepareStatement(queryBuilder);
            int i = 1;
            for (Object value : criterias.values()) {
                preparedStatement.setString(i, value.toString());
                i++;
            }
            resultSet = preparedStatement.executeQuery();

            List<OrderForCooking> ordersForCooking = new ArrayList<>();
            while (resultSet.next()) {
                OrderForCooking order = new OrderForCooking();
                order.setIdOrder(resultSet.getInt(1));
                order.setDishName(resultSet.getString(2));
                order.setPaymentMethod(resultSet.getString(3));
                order.setQuantity(resultSet.getInt(4));
                order.setPrice(resultSet.getBigDecimal(5));
                ordersForCooking.add(order);
            }

            return ordersForCooking;

        } catch (SQLException e) {
            log.error("Error occurred while find orders by criteria", e);
            throw new DaoException("Error while working with database while find orders by dish info", e);
        } finally {
            try {
                connectionPool.closeConnection(connection, preparedStatement, resultSet);
            } catch (SQLException e) {
                log.error("Error while close connection...", e);
            }
        }
    }

    public Map<OrderForCooking, RegistrationUserData> findOrdersUsersByDishInfo(Criteria criteria) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        Map<OrderForCooking, RegistrationUserData> ordersUserDataMap;

        try {
            connection = connectToDataBase(connection);

            Map<String, Object> criterias = criteria.getCriteria();

            StringBuilder sqlBuilder = new StringBuilder("");
            for (String criteriaName : criterias.keySet()) {
                sqlBuilder.append(String.format("%s=? %s", criteriaName.toLowerCase(), AND));
            }
            sqlBuilder = new StringBuilder(sqlBuilder.substring(0, sqlBuilder.length() - AND.length()));
            String queryBuilder = String.format(GET_ORDERS_USERS_BY_DISH_INFO, sqlBuilder);

            preparedStatement = connection.prepareStatement(queryBuilder);
            int i = 1;
            for (Object value : criterias.values()) {
                preparedStatement.setString(i, value.toString());
                i++;
            }
            resultSet = preparedStatement.executeQuery();

            ordersUserDataMap = new LinkedHashMap<>();
            while (resultSet.next()) {
                OrderForCooking order = new OrderForCooking();
                order.setIdOrder(resultSet.getInt(1));
                order.setDishName(resultSet.getString(2));
                order.setPaymentMethod(resultSet.getString(3));
                order.setQuantity(resultSet.getInt(4));
                order.setPrice(resultSet.getBigDecimal(5));
                order.setStatus(resultSet.getString(10));

                RegistrationUserData userData = new RegistrationUserData();
                userData.setName(resultSet.getString(6));
                userData.setSurname(resultSet.getString(7));
                userData.setAddress(resultSet.getString(8));
                userData.setTelephoneNumber(resultSet.getString(9));

                ordersUserDataMap.put(order, userData);
            }

            return ordersUserDataMap;

        } catch (SQLException e) {
            log.error("Error occurred while find orders by criteria", e);
            throw new DaoException("Error while working with database while find orders by dish info", e);
        } finally {
            try {
                connectionPool.closeConnection(connection, preparedStatement, resultSet);
            } catch (SQLException e) {
                log.error("Error while close connection...", e);
            }
        }
    }

    private Map<Dish, Integer> getOrderListByOrderId(Connection connection, int idOrder) throws DaoException {
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        Map<Dish, Integer> orderList = new HashMap<>();

        try {
            preparedStatement = connection.prepareStatement(GET_DISH_LIST_BY_ORDER_ID);
            preparedStatement.setInt(1, idOrder);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Dish dish = new Dish();
                int quantity = resultSet.getInt(4);

                dish.setName(resultSet.getString(1));
                dish.setDescription(resultSet.getString(2));
                dish.setPrice(resultSet.getBigDecimal(3));
                orderList.put(dish, quantity);
            }

            return orderList;
        } catch (SQLException e) {
            log.error("Error working with statements while get order list by order id");
            throw new DaoException("Error when trying to get order list by order id", e);
        }
    }

    private Connection connectToDataBase(Connection connection) throws DaoException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        try {
            connection = connectionPool.takeConnection();
        } catch (InterruptedException | SQLException e) {
            log.error("Error while getting connection from connection pool queue", e);
            throw new DaoException("Error taking connection to database", e);
        }
        return connection;
    }
}
