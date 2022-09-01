package by.gaponenko.restaurant.controller.command.impl;

import by.gaponenko.restaurant.bean.Order;
import by.gaponenko.restaurant.bean.RegistrationUserData;
import by.gaponenko.restaurant.bean.User;
import by.gaponenko.restaurant.bean.criteria.Criteria;
import by.gaponenko.restaurant.bean.criteria.SearchCriteria;
import by.gaponenko.restaurant.controller.JSPPageName;
import by.gaponenko.restaurant.controller.RequestParameterName;
import by.gaponenko.restaurant.controller.command.Command;
import by.gaponenko.restaurant.service.OrderService;
import by.gaponenko.restaurant.service.ServiceException;
import by.gaponenko.restaurant.service.ServiceProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public class GetHistoryOfOrdersCommand implements Command {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private static final ServiceProvider serviceProvider = ServiceProvider.getInstance();
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException, ParseException, ServiceException {
        OrderService orderService = serviceProvider.getOrderService();
        HttpSession session = req.getSession();

        User user = (User) session.getAttribute(RequestParameterName.REQ_PARAM_USER);

        Criteria criteria = new Criteria();
        criteria.add(String.valueOf(SearchCriteria.User.ID_USER), user.getIdUser());

        List<RegistrationUserData> userData = serviceProvider.getUserService().find(criteria);
        List<Order> ordersHistory = orderService.getOrdersHistory(userData.get(0).getIdUser());

        session.setAttribute(RequestParameterName.REQ_PARAM_ORDERS_HISTORY, ordersHistory);

        try {
            req.getRequestDispatcher(JSPPageName.USER_AUTHORIZED_PAGE).forward(req, resp);
        } catch (IOException e) {
            log.error("Error invalid address to forward", e);
        }
    }
}
