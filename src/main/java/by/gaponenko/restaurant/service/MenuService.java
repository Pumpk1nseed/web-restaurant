package by.gaponenko.restaurant.service;

import by.gaponenko.restaurant.bean.Dish;
import by.gaponenko.restaurant.bean.DishCategory;
import by.gaponenko.restaurant.bean.Menu;
import by.gaponenko.restaurant.bean.criteria.Criteria;

import java.util.List;

public interface MenuService {

    Menu getMenu() throws ServiceException;

    List<DishCategory> getDishCategories() throws ServiceException;

    List<Dish> find(Criteria criteria) throws ServiceException;

    boolean editDish (Dish dish) throws ServiceException;

    int removeDish (Criteria criteria) throws ServiceException;

    int addDish (Dish dish) throws ServiceException;
}
