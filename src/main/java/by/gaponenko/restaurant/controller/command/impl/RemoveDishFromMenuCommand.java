package by.gaponenko.restaurant.controller.command.impl;

import by.gaponenko.restaurant.bean.Dish;
import by.gaponenko.restaurant.bean.Menu;
import by.gaponenko.restaurant.bean.criteria.Criteria;
import by.gaponenko.restaurant.bean.criteria.SearchCriteria;
import by.gaponenko.restaurant.controller.ControllerException;
import by.gaponenko.restaurant.controller.JSPPageName;
import by.gaponenko.restaurant.controller.RequestParameterName;
import by.gaponenko.restaurant.controller.command.Command;
import by.gaponenko.restaurant.service.MenuService;
import by.gaponenko.restaurant.service.ServiceException;
import by.gaponenko.restaurant.service.ServiceProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

public class RemoveDishFromMenuCommand implements Command {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private static final MenuService menuService = ServiceProvider.getInstance().getMenuService();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ControllerException {
        int idDishToRemove = Integer.parseInt(req.getParameter(RequestParameterName.REQ_PARAM_ID_DISH));

        try {
            removeDishFromDB(idDishToRemove);

        } catch (ServiceException e) {
            log.error("Error occurred while trying to remove dish from menu", e);
            throw new ControllerException(e);
        }

        removeDishFromSession(req, idDishToRemove);

        try {
            resp.sendRedirect(JSPPageName.MENU_PAGE);
        } catch (IOException e) {
            log.error("Error invalid address to redirect while confirm an order", e);
            throw new ControllerException(e);
        }
    }

    private void removeDishFromDB(int idDishToRemove) throws ServiceException {
        Criteria criteria = new Criteria();
        criteria.add(SearchCriteria.Dish.ID_DISH.toString(), idDishToRemove);

        menuService.removeDish(criteria);
    }

    private void removeDishFromSession(HttpServletRequest req, int idDishToRemove) {
        HttpSession session = req.getSession();
        Menu menu = (Menu) session.getAttribute(RequestParameterName.REQ_PARAM_MENU);

        List<Dish> dishes = menu.getDishes();
        for (Dish dish : dishes) {
            if (dish.getIdDish() == idDishToRemove) {
                dishes.remove(dish);
                break;
            }
        }
    }
}
