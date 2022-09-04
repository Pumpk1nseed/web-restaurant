package by.gaponenko.restaurant.controller.command.impl;

import by.gaponenko.restaurant.bean.Order;
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
import java.util.Map;

public class ConfirmOrderCommand implements Command {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private static final OrderService orderService = ServiceProvider.getInstance().getOrderService();

    private static final String STATUS_CONFIRMED = "confirmed";

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ControllerException {
        int orderIdForConfirm = Integer.parseInt(req.getParameter(RequestParameterName.REQ_PARAM_CONFIRMED_ORDER_ID));

        try {
            orderService.updateOrderStatus(orderIdForConfirm, STATUS_CONFIRMED);
        } catch (ServiceException e) {
            log.error("Error while updating order status", e);
            throw new ControllerException(e);
        }

        removeOrder(req, orderIdForConfirm);

        try {
            resp.sendRedirect(JSPPageName.CONFIRM_PAGE);
        } catch (IOException e) {
            log.error("Error invalid address to forward while confirm an order", e);
            throw new ControllerException(e);
        }
    }

    private void removeOrder(HttpServletRequest req, int orderIdForConfirm) {
        Map<Order, RegistrationUserData> orders = (Map<Order, RegistrationUserData>) req.getSession().getAttribute(RequestParameterName.REQ_PARAM_ORDERS_FOR_CONFIRMATION);
        for (Order order : orders.keySet()) {
            if (order.getIdOrder() == orderIdForConfirm) {
                orders.remove(order);
                break;
            }
            req.getSession().setAttribute(RequestParameterName.REQ_PARAM_ORDERS_FOR_CONFIRMATION, orders);
        }
    }
}
