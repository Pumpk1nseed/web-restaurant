package by.gaponenko.restaurant.controller.command.impl;

import by.gaponenko.restaurant.bean.Order;
import by.gaponenko.restaurant.bean.RegistrationUserData;
import by.gaponenko.restaurant.bean.criteria.Criteria;
import by.gaponenko.restaurant.bean.criteria.SearchCriteria;
import by.gaponenko.restaurant.controller.JSPPageName;
import by.gaponenko.restaurant.controller.RequestParameterName;
import by.gaponenko.restaurant.controller.command.Command;
import by.gaponenko.restaurant.dao.DaoException;
import by.gaponenko.restaurant.service.OrderService;
import by.gaponenko.restaurant.service.ServiceException;
import by.gaponenko.restaurant.service.ServiceProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.ParseException;
import java.util.Map;

public class GoToOrderConfirmationCommand implements Command {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private static final ServiceProvider serviceProvider = ServiceProvider.getInstance();
    private static final String SQL_PREFIX = "ord.";
    private static final String IN_PROCESSING = "in processing";

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException, ParseException, ServiceException, DaoException {
        OrderService orderService = serviceProvider.getOrderService();

        Criteria criteria = new Criteria();
        criteria.add(SQL_PREFIX + SearchCriteria.Order.STATUS.toString(), IN_PROCESSING);

        Map<Order, RegistrationUserData> orderUsedDataMap = orderService.findOrderByUsersInfo(criteria);

        req.getSession().setAttribute(RequestParameterName.REQ_PARAM_ORDERS_FOR_CONFIRMATION, orderUsedDataMap);

        try {
            resp.sendRedirect(JSPPageName.USER_AUTHORIZED_PAGE);
        } catch (IOException e) {
            log.error("Error invalid address to forward while getting orders for confirmation", e);
        }

    }
}
