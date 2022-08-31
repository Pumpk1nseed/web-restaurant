package by.gaponenko.restaurant.controller.command.impl;

import by.gaponenko.restaurant.bean.Dish;
import by.gaponenko.restaurant.bean.Order;
import by.gaponenko.restaurant.bean.PaymentMethod;
import by.gaponenko.restaurant.controller.JSPPageName;
import by.gaponenko.restaurant.controller.RequestParameterName;
import by.gaponenko.restaurant.controller.command.Command;
import by.gaponenko.restaurant.service.PaymentService;
import by.gaponenko.restaurant.service.ServiceException;
import by.gaponenko.restaurant.service.ServiceProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CheckoutCommand implements Command {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private static final ServiceProvider serviceProvider = ServiceProvider.getInstance();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException, ParseException, ServiceException {
        calculatePriceOfOrder(req);
        setPaymentMethodSession(req);

        try {
            resp.sendRedirect(JSPPageName.CHECKOUT_PAGE);
        } catch (IOException e) {
            log.error("Error occured while checkout: invalid address");
            throw new RuntimeException(e);
        }
    }

    private void calculatePriceOfOrder(HttpServletRequest req) {
        String[] strIds = req.getParameterValues(RequestParameterName.REQ_PARAM_ID_DISH);
        String[] strQuantities = req.getParameterValues(RequestParameterName.REQ_PARAM_QUANTITY);

        Map<Integer, Integer> quantityById = new HashMap<>();
        for (int i = 0; i < strIds.length; i++) {
            quantityById.put(Integer.parseInt(strIds[i]), Integer.parseInt(strQuantities[i]));
        }

        HttpSession session = req.getSession();
        Order order = (Order) session.getAttribute(RequestParameterName.REQ_PARAM_ORDER);

        Map<Dish, Integer> orderList = order.getOrderList();
        BigDecimal totalPrice = BigDecimal.valueOf(0);
        for (Dish dish : orderList.keySet()) {
            for (Integer idDish : quantityById.keySet()) {
                if (dish.getIdDish() == idDish) {
                    orderList.put(dish, quantityById.get(idDish));
                    totalPrice = totalPrice.add(
                            dish.getPrice().multiply(BigDecimal.valueOf(quantityById.get(idDish))));
                }
            }
        }

        order.setPrice(totalPrice);
        session.setAttribute(RequestParameterName.REQ_PARAM_ORDER, order);
    }

    private void setPaymentMethodSession(HttpServletRequest req) throws ServiceException {
        HttpSession session = req.getSession();
        List<PaymentMethod> paymentMethods = (List<PaymentMethod>) session.getAttribute(RequestParameterName.REQ_PARAM_PAYMENT_METHODS);
        if (paymentMethods == null) {
            PaymentService paymentService = serviceProvider.getPaymentService();
            paymentMethods = paymentService.getPaymentMethods();

            session.setAttribute(RequestParameterName.REQ_PARAM_PAYMENT_METHODS, paymentMethods);
        }
    }

}
