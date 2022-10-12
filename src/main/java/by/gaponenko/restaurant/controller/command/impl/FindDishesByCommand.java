package by.gaponenko.restaurant.controller.command.impl;

import by.gaponenko.restaurant.bean.Dish;
import by.gaponenko.restaurant.bean.criteria.Criteria;
import by.gaponenko.restaurant.controller.ControllerException;
import by.gaponenko.restaurant.controller.RequestParameterName;
import by.gaponenko.restaurant.controller.command.Command;
import by.gaponenko.restaurant.service.MenuService;
import by.gaponenko.restaurant.service.ServiceException;
import by.gaponenko.restaurant.service.ServiceProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;

public class FindDishesByCommand implements Command {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private static final MenuService menuService = ServiceProvider.getInstance().getMenuService();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ControllerException {
        Integer id = Integer.parseInt(req.getParameter(RequestParameterName.REQ_PARAM_ID_CATEGORY));

        Criteria criteria = new Criteria();
        criteria.add(RequestParameterName.REQ_PARAM_ID_CATEGORY, id);

        try {
            List<Dish> dishes = menuService.find(criteria);
            req.getSession().setAttribute(RequestParameterName.REQ_PARAM_DISHES, dishes);
        } catch (ServiceException e) {
            try {
                req.getRequestDispatcher(MessageFormat.format("/controller?invalidDish=true&errMsgUpdDish={0}", e.getMessage())).forward(req, resp);
            } catch (IOException | ServletException ex) {
                log.error("Error occurred when try to find dishes by criteria", e);
            }
        }
    }
}
