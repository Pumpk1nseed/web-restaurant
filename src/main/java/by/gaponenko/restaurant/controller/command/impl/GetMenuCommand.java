package by.gaponenko.restaurant.controller.command.impl;

import by.gaponenko.restaurant.bean.DishCategory;
import by.gaponenko.restaurant.bean.Menu;
import by.gaponenko.restaurant.controller.ControllerException;
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
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    MenuService menuService = ServiceProvider.getInstance().getMenuService();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ControllerException {

        try {
            Menu menu = menuService.getMenu();
            List<DishCategory> dishCategories = menuService.getDishCategories();

            HttpSession session = req.getSession();
            session.setAttribute("menu", menu);
            session.setAttribute("dish_categories", dishCategories);

            RequestDispatcher requestDispatcher = req.getRequestDispatcher(JSPPageName.MENU_PAGE);
            requestDispatcher.forward(req, resp);

        } catch (IOException e) {
            log.error("Invalid address to forward in get Menu command", e);
            throw new ControllerException(e);
        } catch (ServletException | ServiceException e) {
            log.error("Error occurred in get Menu command", e);
            throw new ControllerException(e);
        }
    }
}
