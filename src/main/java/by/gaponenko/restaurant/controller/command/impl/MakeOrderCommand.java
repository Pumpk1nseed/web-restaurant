package by.gaponenko.restaurant.controller.command.impl;

import by.gaponenko.restaurant.bean.Dish;
import by.gaponenko.restaurant.bean.Order;
import by.gaponenko.restaurant.bean.User;
import by.gaponenko.restaurant.controller.ControllerException;
import by.gaponenko.restaurant.controller.JSPPageName;
import by.gaponenko.restaurant.controller.RequestParameterName;
import by.gaponenko.restaurant.controller.command.Command;
import by.gaponenko.restaurant.service.OrderService;
import by.gaponenko.restaurant.service.ServiceException;
import by.gaponenko.restaurant.service.ServiceProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class MakeOrderCommand implements Command {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private static final ServiceProvider serviceProvider = ServiceProvider.getInstance();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ControllerException {
        int idPaymentMethod = Integer.parseInt(req.getParameter(RequestParameterName.REQ_PARAM_PAYMENT_BY));

        HttpSession session = req.getSession();
        session.setAttribute(RequestParameterName.REQ_PARAM_PAYMENT_BY, idPaymentMethod);

        createOrder(req);

        try {
            session.removeAttribute(RequestParameterName.REQ_PARAM_ORDER);
            session.removeAttribute(RequestParameterName.REQ_PARAM_QUANTITY_OF_DISHES);
            session.removeAttribute(RequestParameterName.REQ_PARAM_PAYMENT_BY);
            session.removeAttribute(RequestParameterName.REQ_PARAM_ORDERS_HISTORY);

            resp.sendRedirect(JSPPageName.ORDER_FINISH_PAGE);
        } catch (IOException e) {
            log.error("Error iccured while finishing the order: invalid address for redirect");
            throw new ControllerException(e);
        }
    }

    public int createOrder(HttpServletRequest req) throws ControllerException {
        HttpSession session = req.getSession();

        User user = (User) session.getAttribute(RequestParameterName.REQ_PARAM_USER);
        Order order = (Order) session.getAttribute(RequestParameterName.REQ_PARAM_ORDER);

        OrderService orderService = serviceProvider.getOrderService();
        try {
            int idOrder = orderService.createOrder(order, user.getIdUser());

            int idPaymentMethod = Integer.parseInt(req.getParameter(RequestParameterName.REQ_PARAM_PAYMENT_BY));

            for (Dish dish : order.getOrderList().keySet()) {
                Integer quantity = order.getOrderList().get(dish);
                orderService.createOrderDetails(idOrder, dish.getIdDish(), quantity, idPaymentMethod);
            }

            return idOrder;

        } catch (ServiceException e) {
            log.error("Error iccured while create an order");
            throw new ControllerException(e);
        }
    }
}
