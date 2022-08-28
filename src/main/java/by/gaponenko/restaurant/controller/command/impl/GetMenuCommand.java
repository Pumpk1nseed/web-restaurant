package by.gaponenko.restaurant.controller.command.impl;

import by.gaponenko.restaurant.bean.DishCategory;
import by.gaponenko.restaurant.bean.Menu;
import by.gaponenko.restaurant.controller.JSPPageName;
import by.gaponenko.restaurant.controller.command.Command;
import by.gaponenko.restaurant.service.MenuService;
import by.gaponenko.restaurant.service.ServiceException;
import by.gaponenko.restaurant.service.ServiceProvider;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

public class GetMenuCommand implements Command {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    MenuService menuService = ServiceProvider.getInstance().getMenuService();

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, ServiceException {

        try {
            Menu menu = menuService.getMenu();
            List<DishCategory> dishCategories = menuService.getDishCategories();

            HttpSession session = req.getSession();
            session.setAttribute("menu", menu);
            session.setAttribute("dish_categories", dishCategories);

            RequestDispatcher requestDispatcher = req.getRequestDispatcher(JSPPageName.MENU_PAGE);
            requestDispatcher.forward(req, resp);

        } catch (ServiceException e) {
            //исправить
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ServletException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
