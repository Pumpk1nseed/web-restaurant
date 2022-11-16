package by.gaponenko.restaurant.dao.impl;

import by.gaponenko.restaurant.bean.PaymentMethod;
import by.gaponenko.restaurant.dao.DaoException;
import by.gaponenko.restaurant.dao.PaymentDao;
import by.gaponenko.restaurant.dao.pool.ConnectionPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLPaymentDao implements PaymentDao {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    ConnectionPool connectionPool = ConnectionPool.getInstance();

    private static final String GET_PAYMENT_METHODS = "SELECT * FROM payment_methods";
    private static final String FIND_BILL_BY_ORDER_ID = "SELECT * FROM bill WHERE id_order = ?;";
    private static final String CREATE_BILL= "INSERT INTO bill (id_order, date, status) VALUES(?,?,?);";

    private static final String UPDATE_BILL_STATUS = "UPDATE bill SET date=?, status=? where id_bill=?;";
    private static final String UNPAID = "unpaid";

    @Override
    public List<PaymentMethod> getPaymentMethods() throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<PaymentMethod> methods = null;

        try{
            connection = connectToDataBase(connection);

            preparedStatement = connection.prepareStatement(GET_PAYMENT_METHODS);
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst()){
                return methods;
            }

            methods = new ArrayList<>();
            while(resultSet.next()){
                PaymentMethod method = new PaymentMethod();

                method.setIdPaymentMethod(resultSet.getInt(1));
                method.setName(resultSet.getString(2));

                methods.add(method);
            }

            return methods;

        } catch (DaoException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new DaoException("Error when trying to create a methods of payment query", e);
        } finally {
            try {
                connectionPool.closeConnection(connection, preparedStatement, resultSet);
            } catch (SQLException e) {
                log.error("Error while close connection...", e);
            }
        }
    }

    @Override
    public int createBill(int orderIdForBill) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectToDataBase(connection);

            preparedStatement = connection.prepareStatement(FIND_BILL_BY_ORDER_ID);
            preparedStatement.setInt(1, orderIdForBill);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt(1);
            }

            preparedStatement = connection.prepareStatement(CREATE_BILL);
            preparedStatement.setInt(1, orderIdForBill);
            preparedStatement.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
            preparedStatement.setString(3, UNPAID);
            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement(FIND_BILL_BY_ORDER_ID);
            preparedStatement.setInt(1, orderIdForBill);
            resultSet = preparedStatement.executeQuery();

            resultSet.next();
            return resultSet.getInt(1);

        } catch (DaoException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new DaoException("Error when trying to create a bill", e);
        } finally {
            try {
                connectionPool.closeConnection(connection, preparedStatement, resultSet);
            } catch (SQLException e) {
                log.error("Error while close connection...", e);
            }
        }
    }

    @Override
    public boolean updateBillStatus(int idBill, String status) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectToDataBase(connection);

            preparedStatement = connection.prepareStatement(UPDATE_BILL_STATUS);
            preparedStatement.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
            preparedStatement.setString(2, status);
            preparedStatement.setInt(3, idBill);
            preparedStatement.executeUpdate();

            return true;

        } catch (SQLException e) {
            log.error("Error occurred while updating bill status", e);
            throw new DaoException("Error while working with database while updating bill status", e);
        } finally {
            try {
                connectionPool.closeConnection(connection, preparedStatement, resultSet);
            } catch (SQLException e) {
                log.error("Error while close connection...", e);
            }
        }
    }

    private Connection connectToDataBase(Connection connection) throws DaoException {
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
