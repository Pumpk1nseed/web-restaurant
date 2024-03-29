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
import by.gaponenko.restaurant.service.validation.DishValidator;
import com.oracle.wls.shaded.org.apache.bcel.generic.ATHROW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class MenuServiceImpl implements MenuService {
    private static final DishValidator validator = DishValidator.getInstance();
    private final Logger log = LoggerFactory.getLogger(this.getClass());
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

    @Override
    public boolean editDish(Dish dish) throws ServiceException {
        DishValidator.validate(dish);

        try {
            return menuDao.editDish(dish);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public int removeDish(Criteria criteria) throws ServiceException {
        DishValidator.validate(criteria);

        try {
            return menuDao.removeDish(criteria);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public int addDish(Dish dish) throws ServiceException {
        DishValidator.validate(dish);

        try {
            return menuDao.addDish(dish);
        } catch (DaoException e) {
            throw new ServiceException();
        }
    }

}
