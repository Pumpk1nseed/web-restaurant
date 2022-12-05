package by.gaponenko.restaurant.controller.command.impl;

import by.gaponenko.restaurant.bean.Order;
import by.gaponenko.restaurant.bean.OrderForCooking;
import by.gaponenko.restaurant.bean.RegistrationUserData;
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
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class GoToOrderDeliveringCommand implements Command {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    OrderService orderService = ServiceProvider.getInstance().getOrderService();

    private static final String SQL_PREFIX = "ord.";
    private static final String STATUS_COOKED = "cooked";

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ControllerException {
        Criteria criteria = new Criteria();
        criteria.add(SQL_PREFIX + SearchCriteria.Order.STATUS.name(), STATUS_COOKED);

        try {
            Map<OrderForCooking, RegistrationUserData> ordersForDeliveryMap = orderService.findOrdersUsersByDishInfo(criteria);

            Map<OrderForCooking, RegistrationUserData> ordersForDeliveryGroupByDishMap = new LinkedHashMap<>();
            long idOrderPrevious = 0;
            StringBuilder namesOfDishes = new StringBuilder("");
            BigDecimal totalPrice;
            for (OrderForCooking order : ordersForDeliveryMap.keySet()) {
                RegistrationUserData userData = ordersForDeliveryMap.get(order);
                if (idOrderPrevious != order.getIdOrder()) {

                    namesOfDishes = new StringBuilder(order.getDishName()).append(" (x").append(order.getQuantity()).append(" x").append(order.getPrice()).append(")");
                    totalPrice = order.getPrice().multiply(new BigDecimal(order.getQuantity()));

                    order.setDishName(namesOfDishes.toString());
                    order.setPrice(totalPrice);
                    ordersForDeliveryGroupByDishMap.put(order, userData);
                    idOrderPrevious = order.getIdOrder();
                } else {

                    namesOfDishes.append("<hr>").append(order.getDishName()).append(" (x").append(order.getQuantity()).append(" x").append(order.getPrice()).append(")");

                    for (OrderForCooking orderForDeliver : ordersForDeliveryGroupByDishMap.keySet()) {
                        if (orderForDeliver.getIdOrder() == order.getIdOrder()) {
                            totalPrice = orderForDeliver.getPrice().add(order.getPrice().multiply(new BigDecimal(order.getQuantity())));
                            ordersForDeliveryGroupByDishMap.remove(orderForDeliver);

                            orderForDeliver.setDishName(namesOfDishes.toString());
                            orderForDeliver.setPrice(totalPrice);
                            ordersForDeliveryGroupByDishMap.put(orderForDeliver, userData);
                            break;
                        }
                    }
                    idOrderPrevious = order.getIdOrder();
                }
            }
            req.getSession().setAttribute(RequestParameterName.REQ_PARAM_ORDERS_FOR_DELIVERING, ordersForDeliveryGroupByDishMap);

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
