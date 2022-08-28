package by.gaponenko.restaurant.dao;

import by.gaponenko.restaurant.bean.DishCategory;
import by.gaponenko.restaurant.bean.Menu;

import java.util.List;

public interface MenuDao {

    Menu getMenu() throws DaoException;

    List<DishCategory> getDishCategories() throws DaoException;

}
