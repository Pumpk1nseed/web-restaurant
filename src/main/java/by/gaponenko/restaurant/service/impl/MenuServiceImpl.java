package by.gaponenko.restaurant.service.impl;

import by.gaponenko.restaurant.bean.Dish;
import by.gaponenko.restaurant.bean.DishCategory;
import by.gaponenko.restaurant.bean.Menu;
import by.gaponenko.restaurant.bean.criteria.Criteria;
import by.gaponenko.restaurant.dao.DaoException;
import by.gaponenko.restaurant.dao.DaoProvider;
import by.gaponenko.restaurant.dao.MenuDao;
import by.gaponenko.restaurant.service.MenuService;
import by.gaponenko.restaurant.service.ServiceException;
import by.gaponenko.restaurant.service.Validation.DishValidator;

import java.util.List;

public class MenuServiceImpl implements MenuService{
    private static final DishValidator validator = DishValidator.getInstance();
    private static final MenuDao menuDao = DaoProvider.getInstance().getMenuDao();

    public Menu getMenu() throws ServiceException {
        try {
            return menuDao.getMenu();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<DishCategory> getDishCategories() throws ServiceException {
        try {
            return menuDao.getDishCategories();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public List<Dish> find(Criteria criteria) throws ServiceException {
        DishValidator.validate(criteria);

        try {
            return menuDao.find(criteria);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

}
