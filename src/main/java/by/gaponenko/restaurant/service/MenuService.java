package by.gaponenko.restaurant.service;

import by.gaponenko.restaurant.bean.DishCategory;
import by.gaponenko.restaurant.bean.Menu;

import java.util.List;

public interface MenuService {

    Menu getMenu() throws ServiceException;

    List<DishCategory> getDishCategories() throws ServiceException;
}
