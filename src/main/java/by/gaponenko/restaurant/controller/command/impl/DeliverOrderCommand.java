package by.gaponenko.restaurant.controller.command.impl;

import by.gaponenko.restaurant.bean.OrderForCooking;
import by.gaponenko.restaurant.bean.RegistrationUserData;
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
import java.util.List;
import java.util.Map;

public class DeliverOrderCommand implements Command {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private static final OrderService orderService = ServiceProvider.getInstance().getOrderService();

    private static final String STATUS_COMPLETED = "completed";

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ControllerException {
        int orderIdForDelivering = Integer.parseInt(req.getParameter(RequestParameterName.REQ_PARAM_DELIVERED_ORDER_ID));

        try {
            orderService.updateOrderStatus(orderIdForDelivering, STATUS_COMPLETED);
        } catch (ServiceException e) {
            log.error("Error while updating order status", e);
            throw new ControllerException(e);
        }

        removeOrder(req, orderIdForDelivering);

        try {
            resp.sendRedirect(JSPPageName.DELIVERING_PAGE);
        } catch (IOException e) {
            log.error("Error invalid address to redirect while deliver an order", e);
            throw new ControllerException(e);
        }
    }

    private void removeOrder(HttpServletRequest req, int orderIdForDelivering) {
        Map<OrderForCooking, RegistrationUserData> orders = (Map<OrderForCooking, RegistrationUserData>) req.getSession().getAttribute(RequestParameterName.REQ_PARAM_ORDERS_FOR_DELIVERING);
        for (OrderForCooking order : orders.keySet()) {
            if (order.getIdOrder() == orderIdForDelivering) {
                orders.remove(order);
                break;
            }
        }
        req.getSession().setAttribute(RequestParameterName.REQ_PARAM_ORDERS_FOR_DELIVERING, orders);
    }
}

