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

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class SQLMenuDao implements MenuDao {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private static final String GET_MENU = "SELECT * FROM menu where status = ?;";
    private static final String GET_DISH_CATEGORIES = "SELECT * FROM dish_categories";
    private static final String GET_DISH_BY_CRITERIA = "SELECT * FROM menu WHERE ";
    private static final String AND = "AND ";

    ConnectionPool connectionPool = ConnectionPool.getInstance();
    private Connection connection;

    @Override
    public Menu getMenu() throws DaoException {
        PreparedStatement preparedStatement;
        ResultSet resultSet;

        Menu menu = null;

        try {
            connection = connectToDataBase();

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

            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            log.error("Error working with statements while getting menu", e);
            throw new DaoException("Error while getting menu", e);
        }
        return menu;
    }

    @Override
    public List<DishCategory> getDishCategories() throws DaoException {
        PreparedStatement preparedStatement;
        ResultSet resultSet;

        List<DishCategory> categories = null;

        try {
            connection = connectToDataBase();

            preparedStatement = connection.prepareStatement(GET_DISH_CATEGORIES);
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                return categories;
            }

            categories = new ArrayList<>();
            while (resultSet.next()) {
                DishCategory category = new DishCategory();
                category.setIdCategory(resultSet.getInt(1));
                category.setName(resultSet.getString(2));

                categories.add(category);
            }

            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            log.error("Error working with statements while getting dish categories", e);
            throw new DaoException("Error while getting dish categories", e);
        }
        return categories;
    }

    @Override
    public List<Dish> find(Criteria criteria) throws DaoException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        Map<String, Object> criteriaMap = criteria.getCriteria();
        List<Dish> dishes = null;

        try {
            connection = connectToDataBase();

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

            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (DaoException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            log.error("Error working with statements while getting dish by criteria", e);
            throw new DaoException("Error while getting dish", e);
        }
        return dishes;
    }

    private Connection connectToDataBase() throws DaoException {
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
