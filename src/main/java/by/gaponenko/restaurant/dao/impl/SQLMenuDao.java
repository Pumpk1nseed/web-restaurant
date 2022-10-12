package by.gaponenko.restaurant.dao.impl;

import by.gaponenko.restaurant.bean.Dish;
import by.gaponenko.restaurant.bean.DishCategory;
import by.gaponenko.restaurant.bean.Menu;
import by.gaponenko.restaurant.bean.criteria.Criteria;
import by.gaponenko.restaurant.dao.DaoException;
import by.gaponenko.restaurant.dao.MenuDao;
import by.gaponenko.restaurant.dao.pool.ConnectionPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SQLMenuDao implements MenuDao {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    ConnectionPool connectionPool = ConnectionPool.getInstance();

    private static final String GET_MENU = "SELECT * FROM menu where status = ?;";
    private static final String GET_DISH_CATEGORIES = "SELECT * FROM dish_categories";
    private static final String GET_DISH_BY_CRITERIA = "SELECT * FROM menu WHERE ";
    private static final String EDIT_DISH = "UPDATE menu SET name=?, price=?, description=?, photo=? where id_dish=?";
    private static final String AND = "AND ";

    @Override
    public Menu getMenu() throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        Menu menu = null;

        try {
            connection = connectToDataBase(connection);

            preparedStatement = connection.prepareStatement(GET_MENU);
            preparedStatement.setString(1, "active");
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                return menu;
            }

            menu = new Menu();
            while (resultSet.next()) {
                Dish dish = new Dish();
                dish.setIdDish(resultSet.getInt(1));
                dish.setName(resultSet.getString(2));
                dish.setPrice(resultSet.getBigDecimal(3));
                dish.setDescription(resultSet.getString(4));
                dish.setPhotoUrl(resultSet.getString(5));
                dish.setIdCategory(resultSet.getInt(6));
                dish.setStatus(resultSet.getString(7));

                menu.add(dish);
            }

            return menu;

        } catch (SQLException e) {
            log.error("Error working with statements while getting menu", e);
            throw new DaoException("Error while getting menu", e);
        } finally {
            try {
                connectionPool.closeConnection(connection, preparedStatement, resultSet);
            } catch (SQLException e) {
                log.error("Error while close connection...", e);
            }
        }
    }

    @Override
    public List<DishCategory> getDishCategories() throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        List<DishCategory> categories = null;

        try {
            connection = connectToDataBase(connection);

            preparedStatement = connection.prepareStatement(GET_DISH_CATEGORIES);
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                return null;
            }

            categories = new ArrayList<>();
            while (resultSet.next()) {
                DishCategory category = new DishCategory();
                category.setIdCategory(resultSet.getInt(1));
                category.setName(resultSet.getString(2));

                categories.add(category);
            }

            return categories;
        } catch (SQLException e) {
            log.error("Error working with statements while getting dish categories", e);
            throw new DaoException("Error while getting dish categories", e);
        } finally {
            try {
                connectionPool.closeConnection(connection, preparedStatement, resultSet);
            } catch (SQLException e) {
                log.error("Error while close connection...", e);
            }
        }
    }

    @Override
    public List<Dish> find(Criteria criteria) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        Map<String, Object> criteriaMap = criteria.getCriteria();
        List<Dish> dishes = null;

        try {
            connection = connectToDataBase(connection);

            StringBuilder queryDishBuilder = new StringBuilder(GET_DISH_BY_CRITERIA);
            for (String criteriaName : criteriaMap.keySet()) {
                queryDishBuilder.append(String.format("%s=? %s", criteriaName.toLowerCase(), AND));
            }
            queryDishBuilder = new StringBuilder(queryDishBuilder.substring(0, queryDishBuilder.length() - AND.length()));

            preparedStatement = connection.prepareStatement(queryDishBuilder.toString());
            int i = 1;
            for (Object value : criteriaMap.values()) {
                preparedStatement.setString(i, value.toString());
                i++;
            }
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                return dishes;
            }

            dishes = new ArrayList<>();
            while (resultSet.next()) {
                Dish dish = new Dish();
                dish.setIdDish(resultSet.getInt(1));
                dish.setName(resultSet.getString(2));
                dish.setPrice(resultSet.getBigDecimal(3));
                dish.setDescription(resultSet.getString(4));
                dish.setPhotoUrl(resultSet.getString(5));
                dish.setIdCategory(resultSet.getInt(6));
                dish.setStatus(resultSet.getString(7));

                dishes.add(dish);
            }

            return dishes;

        } catch (SQLException e) {
            log.error("Error when working with statements while getting dish by criteria", e);
            throw new DaoException("Error while getting dish", e);
        } finally {
            try {
                connectionPool.closeConnection(connection, preparedStatement, resultSet);
            } catch (SQLException e) {
                log.error("Error while close connection...", e);
            }
        }
    }

    @Override
    public boolean editDish(Dish dish) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = connectToDataBase(connection);

            preparedStatement = connection.prepareStatement(EDIT_DISH);
            preparedStatement.setString(1, dish.getName());
            preparedStatement.setBigDecimal(2, dish.getPrice());
            preparedStatement.setString(3, dish.getDescription());
            preparedStatement.setString(4, dish.getPhotoUrl());
            preparedStatement.setInt(5, dish.getIdDish());
            preparedStatement.executeUpdate();

            return true;

        } catch (SQLException e) {
            log.error("Error when working with statements while editing dish", e);
            throw new DaoException("Error while editing dish", e);
        } finally {
            try {
                connectionPool.closeConnection(connection, preparedStatement, null);
            } catch (SQLException e) {
                log.error("Error while close connection...", e);
            }
        }
    }

    private Connection connectToDataBase(Connection connection) throws DaoException {
        try {
            connection = connectionPool.takeConnection();
        } catch (InterruptedException e) {
            log.error("Error while getting connection from connection pool queue", e);
            throw new DaoException("Error taking connection to database", e);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }
}
