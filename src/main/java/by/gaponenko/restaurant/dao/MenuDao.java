package by.gaponenko.restaurant.dao;

import by.gaponenko.restaurant.bean.Dish;
import by.gaponenko.restaurant.bean.DishCategory;
import by.gaponenko.restaurant.bean.Menu;
import by.gaponenko.restaurant.bean.criteria.Criteria;

import java.util.List;

public interface MenuDao {

    Menu getMenu() throws DaoException;

    List<DishCategory> getDishCategories() throws DaoException;

    List<Dish> find(Criteria criteria) throws DaoException;

    boolean editDish(Dish dish) throws DaoException;

    int removeDish(Criteria criteria) throws DaoException;

    int addDish(Dish dish) throws DaoException;

}
