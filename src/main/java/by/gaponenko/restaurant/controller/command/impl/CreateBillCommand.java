package by.gaponenko.restaurant.controller.command.impl;

import by.gaponenko.restaurant.bean.Order;
import by.gaponenko.restaurant.bean.OrderForCooking;
import by.gaponenko.restaurant.bean.RegistrationUserData;
import by.gaponenko.restaurant.bean.User;
import by.gaponenko.restaurant.bean.criteria.Criteria;
import by.gaponenko.restaurant.bean.criteria.SearchCriteria;
import by.gaponenko.restaurant.controller.ControllerException;
import by.gaponenko.restaurant.controller.JSPPageName;
import by.gaponenko.restaurant.controller.RequestParameterName;
import by.gaponenko.restaurant.controller.command.Command;
import by.gaponenko.restaurant.service.OrderService;
import by.gaponenko.restaurant.service.PaymentService;
import by.gaponenko.restaurant.service.ServiceException;
import by.gaponenko.restaurant.service.ServiceProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;

public class CreateBillCommand implements Command {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private static final PaymentService paymentService = ServiceProvider.getInstance().getPaymentService();
    OrderService orderService = ServiceProvider.getInstance().getOrderService();

    private static final String SQL_PREFIX = "ord.";

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ControllerException {
        Integer idOrderForBill;
        User currentUser = (User) req.getSession().getAttribute(RequestParameterName.REQ_PARAM_USER);
        int id_userRole = currentUser.getIdRole();

        if (id_userRole == 4) {
            idOrderForBill = Integer.parseInt(req.getParameter(RequestParameterName.REQ_PARAM_ID_ORDER_FOR_BILL));
        } else {
            idOrderForBill = (Integer) req.getSession().getAttribute(RequestParameterName.REQ_PARAM_ID_ORDER_FOR_BILL);
        }

        int idBill;

        Criteria criteria = new Criteria();
        criteria.add(SQL_PREFIX + SearchCriteria.Order.ID_ORDER.name(), idOrderForBill.toString());

        try {
            idBill = paymentService.createBill(idOrderForBill);
            Map<Order, RegistrationUserData> orderForBillMap = orderService.findOrdersByUsersInfo(criteria);

            req.getSession().setAttribute(RequestParameterName.REQ_PARAM_ID_BILL, idBill);
            req.getSession().setAttribute(RequestParameterName.REQ_PARAM_ORDER_FOR_BILL, orderForBillMap);

        } catch (ServiceException e) {
            log.error("Error while getting a bill", e);
            throw new ControllerException(e);
        }

        try {
            req.getSession().removeAttribute(RequestParameterName.REQ_PARAM_ID_ORDER_FOR_BILL);

            resp.sendRedirect(JSPPageName.BILL_PAGE);
        } catch (IOException e) {
            log.error("Error invalid address to redirect while create a bill", e);
            throw new ControllerException(e);
        }
    }
}
