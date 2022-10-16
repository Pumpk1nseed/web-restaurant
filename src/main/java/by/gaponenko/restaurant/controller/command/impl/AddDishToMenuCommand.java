package by.gaponenko.restaurant.controller.command.impl;

import by.gaponenko.restaurant.bean.Dish;
import by.gaponenko.restaurant.bean.Menu;
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
import java.math.BigDecimal;
import java.util.List;

public class AddDishToMenuCommand implements Command {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    MenuService menuService = ServiceProvider.getInstance().getMenuService();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ControllerException {

        try {
            Integer categoryId = Integer.parseInt(req.getParameter(RequestParameterName.REQ_PARAM_ID_CATEGORY));
            String dishName = req.getParameter(RequestParameterName.REQ_PARAM_DISH_NAME);
            String description = req.getParameter(RequestParameterName.REQ_PARAM_DISH_DESCRIPTION);
            BigDecimal price = new BigDecimal(req.getParameter(RequestParameterName.REQ_PARAM_DISH_PRICE));
            String photoUrl = req.getParameter(RequestParameterName.REQ_PARAM_PHOTO_URL);

            Dish newDish = new Dish();
            newDish.setIdCategory(categoryId);
            newDish.setName(dishName);
            newDish.setDescription(description);
            newDish.setPrice(price);
            newDish.setPhotoUrl(photoUrl);

            int idNewDish = menuService.addDish(newDish);
            addDishToSession(req, newDish, idNewDish);

            resp.sendRedirect(JSPPageName.MENU_PAGE);

        } catch (ServiceException e) {
            log.error("Error occurred while trying to add dish to menu", e);
            throw new ControllerException(e);
        } catch (IOException e) {
            log.error("Error invalid address to redirect while confirm an order", e);
            throw new ControllerException(e);
        }
    }

    private void addDishToSession(HttpServletRequest req, Dish newDish, int idNewDish) {
        HttpSession session = req.getSession();
        Menu menu = (Menu) session.getAttribute(RequestParameterName.REQ_PARAM_MENU);

        List<Dish> dishes = menu.getDishes();
        Dish newDishForSession = new Dish(idNewDish, newDish.getName(), newDish.getDescription(), newDish.getIdCategory(), newDish.getPhotoUrl(), newDish.getPrice(), "active");
        dishes.add(newDishForSession);
    }
}
