package by.gaponenko.restaurant.controller.command.impl;

import by.gaponenko.restaurant.bean.OrderForCooking;
import by.gaponenko.restaurant.bean.criteria.Criteria;
import by.gaponenko.restaurant.bean.criteria.SearchCriteria;
import by.gaponenko.restaurant.controller.ControllerException;
import by.gaponenko.restaurant.controller.JSPPageName;
import by.gaponenko.restaurant.controller.RequestParameterName;
import by.gaponenko.restaurant.controller.command.Command;
import by.gaponenko.restaurant.service.OrderService;
import by.gaponenko.restaurant.service.ServiceException;
import by.gaponenko.restaurant.service.ServiceProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GoToOrderDeliveringCommand implements Command {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    OrderService orderService = ServiceProvider.getInstance().getOrderService();

    private static final String STATUS_COOKED = "cooked";

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ControllerException {
        Criteria criteria = new Criteria();
        criteria.add(SearchCriteria.Order.STATUS.name(), STATUS_COOKED);

        try {
            List<OrderForCooking> ordersForCooking = orderService.findOrderByDishInfo(criteria);

            List<OrderForCooking> ordersForCookingGroupByDish = new ArrayList<>();
            long idOrderPrevious = 0;
            StringBuilder namesOfDishes = new StringBuilder("");
            int j = -1;
            for (OrderForCooking order : ordersForCooking) {
                if (idOrderPrevious != order.getIdOrder()) {
                    j++;

                    namesOfDishes = new StringBuilder(order.getDishName()).append(" (x").append(order.getQuantity()).append(")");

                    order.setDishName(namesOfDishes.toString());
                    ordersForCookingGroupByDish.add(order);
                    idOrderPrevious = order.getIdOrder();
                } else {
                    namesOfDishes.append("<hr>").append(order.getDishName()).append(" (x").append(order.getQuantity()).append(")");
                    ordersForCookingGroupByDish.get(j).setDishName(namesOfDishes.toString());
                }
            }

            req.getSession().setAttribute(RequestParameterName.REQ_PARAM_ORDERS_FOR_DELIVERING, ordersForCookingGroupByDish);

            resp.sendRedirect(JSPPageName.DELIVERING_PAGE);

        } catch (ServiceException e) {
            log.error("Error while getting orders for delivering", e);
            throw new ControllerException(e);
        } catch (IOException e) {
            log.error("Invalid address redirect while getting orders for delivering", e);
            throw new ControllerException(e);
        }
    }
}
