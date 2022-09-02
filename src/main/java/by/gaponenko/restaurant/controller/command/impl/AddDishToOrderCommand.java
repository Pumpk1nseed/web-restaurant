package by.gaponenko.restaurant.controller.command.impl;

import by.gaponenko.restaurant.bean.Dish;
import by.gaponenko.restaurant.bean.Order;
import by.gaponenko.restaurant.bean.criteria.Criteria;
import by.gaponenko.restaurant.bean.criteria.SearchCriteria;
import by.gaponenko.restaurant.controller.ControllerException;
import by.gaponenko.restaurant.controller.RequestParameterName;
import by.gaponenko.restaurant.controller.command.Command;
import by.gaponenko.restaurant.service.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

public class AddDishToOrderCommand implements Command {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    MenuService menuService = ServiceProvider.getInstance().getMenuService();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ControllerException {
        HttpSession session = req.getSession();
        PrintWriter writer;
        Criteria criteria;
        Order order = (Order) session.getAttribute(RequestParameterName.REQ_PARAM_ORDER);

        order = checkExistance(session, order);
        try {
            writer = resp.getWriter();

            criteria = new Criteria();
            criteria.add(SearchCriteria.Dish.ID_DISH.toString(), req.getParameter(RequestParameterName.REQ_PARAM_ID_DISH));

            Dish dish = menuService.find(criteria).get(0);
            Integer newQuantity = Integer.parseInt(req.getParameter(RequestParameterName.REQ_PARAM_QUANTITY));

            if (order.getOrderList().containsKey(dish)) {
                Integer currentQuantity = order.getOrderList().get(dish);
                currentQuantity += newQuantity;

                order.getOrderList().put(dish, currentQuantity);
            } else {
                order.getOrderList().put(dish, newQuantity);
            }

            setDishQuantitySession(order.getOrderList(), session);

        } catch (ServiceException | IOException e) {
            log.error("Error occurred while trying to add dish to order", e);
            throw new ControllerException(e);
        }
    }

    private Order checkExistance(HttpSession session, Order order) {
        if (order == null) {
            order = new Order();
            session.setAttribute(RequestParameterName.REQ_PARAM_ORDER, order);

            Integer quantityDish = 0;
            session.setAttribute(RequestParameterName.REQ_PARAM_QUANTITY_OF_DISHES, quantityDish);
        }
        return order;
    }

    private void setDishQuantitySession(Map<Dish, Integer> orderList, HttpSession session) {
        Integer count = 0;

        for (Integer quantity : orderList.values()) {
            count += quantity;
        }
        session.setAttribute(RequestParameterName.REQ_PARAM_QUANTITY_OF_DISHES, count);
    }
}
