package by.gaponenko.restaurant.dao.impl;

import by.gaponenko.restaurant.bean.Dish;
import by.gaponenko.restaurant.bean.DishCategory;
import by.gaponenko.restaurant.bean.Menu;
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

public class SQLMenuDao implements MenuDao {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private static final String GET_MENU = "SELECT * FROM menu where status = ?;";
    private static final String GET_DISH_CATEGORIES = "SELECT * FROM dish_categories";
    private Connection connection;

    @Override
    public Menu getMenu() throws DaoException {
        PreparedStatement preparedStatement;
        ResultSet resultSet;

        Menu menu = null;

        try {
            connection = connectToDataBase();

            preparedStatement = connection.prepareStatement(GET_MENU);
            preparedStatement.setString(1,"active");
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
