package by.gaponenko.restaurant.dao.impl;

import by.gaponenko.restaurant.bean.PaymentMethod;
import by.gaponenko.restaurant.dao.DaoException;
import by.gaponenko.restaurant.dao.PaymentDao;
import by.gaponenko.restaurant.dao.pool.ConnectionPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SQLPaymentDao implements PaymentDao {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private static final String GET_PAYMENT_METHODS = "SELECT * FROM payment_methods";

    private Connection connection;
    @Override
    public List<PaymentMethod> getPaymentMethods() throws DaoException {
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        List<PaymentMethod> methods = null;

        try{
            connection = connectToDataBase();

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

            resultSet.close();
            preparedStatement.close();
            connection.close();

        } catch (DaoException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new DaoException("Error when trying to create a methods of payment query", e);
        }
        return methods;
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
