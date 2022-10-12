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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

public class EditDishCommand implements Command {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private static final MenuService menuService = ServiceProvider.getInstance().getMenuService();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ControllerException {

        try {
            int idEditedDish = Integer.parseInt(req.getParameter(RequestParameterName.REQ_PARAM_ID_EDITED_DISH));
            String newDishName = req.getParameter(RequestParameterName.REQ_PARAM_DISH_NAME);
            String description = req.getParameter(RequestParameterName.REQ_PARAM_DISH_DESCRIPTION);
            BigDecimal newPrice = new BigDecimal(req.getParameter(RequestParameterName.REQ_PARAM_DISH_PRICE));
            String photoUrl = req.getParameter(RequestParameterName.REQ_PARAM_PHOTO_URL);

            Dish editedDish = new Dish();
            editedDish.setIdDish(idEditedDish);
            editedDish.setName(newDishName);
            editedDish.setDescription(description);
            editedDish.setPrice(newPrice);
            editedDish.setPhotoUrl(photoUrl);

            menuService.editDish(editedDish);
            editDishInSession(req, editedDish);

            resp.sendRedirect(JSPPageName.EDIT_SUCCESS_PAGE);

        } catch (ServiceException e) {
            log.error("Error occurred while trying to edit dish", e);
            throw new ControllerException(e);
        } catch (IOException e) {
            log.error("Invalid address to redirect while editing dish", e);
            throw new ControllerException(e);
        }
    }

    private void editDishInSession(HttpServletRequest req, Dish editedDish){
        Menu menu = (Menu) req.getSession().getAttribute(RequestParameterName.REQ_PARAM_MENU);
        List<Dish> dishes = menu.getDishes();

        for (Dish dish : dishes){
            if (dish.getIdDish() == editedDish.getIdDish()){
                dish.setName(editedDish.getName());
                dish.setDescription(editedDish.getDescription());
                dish.setPrice(editedDish.getPrice());
                dish.setPhotoUrl(editedDish.getPhotoUrl());
                break;
            }
        }
    }

}
