package by.gaponenko.restaurant.controller.command.impl;

import by.gaponenko.restaurant.bean.Dish;
import by.gaponenko.restaurant.bean.Order;
import by.gaponenko.restaurant.controller.ControllerException;
import by.gaponenko.restaurant.controller.JSPPageName;
import by.gaponenko.restaurant.controller.RequestParameterName;
import by.gaponenko.restaurant.controller.command.Command;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;

public class RemoveDishFromOrderCommand implements Command {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ControllerException {
        int idRemove = Integer.parseInt(req.getParameter(RequestParameterName.REQ_PARAM_ID_DISH));

        HttpSession session = req.getSession();
        Order order = (Order) session.getAttribute(RequestParameterName.REQ_PARAM_ORDER);

        Map<Dish, Integer> orderList = order.getOrderList();
        for (Dish dish : orderList.keySet()) {
            if (dish.getIdDish() == idRemove) {
                orderList.remove(dish);
                int quantity = (Integer) session.getAttribute(RequestParameterName.REQ_PARAM_QUANTITY_OF_DISHES);
                quantity--;
                session.setAttribute(RequestParameterName.REQ_PARAM_QUANTITY_OF_DISHES, quantity);
                break;
            }
        }

        if (orderList.size() == 0) {
            order = null;
            session.removeAttribute(RequestParameterName.REQ_PARAM_QUANTITY_OF_DISHES);
        }

        session.setAttribute(RequestParameterName.REQ_PARAM_ORDER, order);

        try {
            resp.sendRedirect(JSPPageName.BASKET_PAGE);
        } catch (IOException e) {
            log.error("Error invalid address to redirect while remove order", e);
            throw new ControllerException(e);
        }
    }
}
