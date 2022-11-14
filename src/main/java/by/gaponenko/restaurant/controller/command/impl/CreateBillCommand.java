package by.gaponenko.restaurant.controller.command.impl;

import by.gaponenko.restaurant.bean.Bill;
import by.gaponenko.restaurant.controller.ControllerException;
import by.gaponenko.restaurant.controller.JSPPageName;
import by.gaponenko.restaurant.controller.RequestParameterName;
import by.gaponenko.restaurant.controller.command.Command;
import by.gaponenko.restaurant.service.PaymentService;
import by.gaponenko.restaurant.service.ServiceException;
import by.gaponenko.restaurant.service.ServiceProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class CreateBillCommand implements Command {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private static final PaymentService paymentService = ServiceProvider.getInstance().getPaymentService();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ControllerException {
        Integer idOrderForBill = Integer.parseInt(req.getParameter(RequestParameterName.REQ_PARAM_ID_ORDER_FOR_BILL));

        Bill bill;
        try {
            bill = paymentService.createBill(idOrderForBill);
            req.getSession().setAttribute(RequestParameterName.REQ_PARAM_BILL, bill);
        } catch (ServiceException e) {
            log.error("Error while getting a bill", e);
            throw new ControllerException(e);
        }

        try {
            resp.sendRedirect(JSPPageName.BILL_PAGE);
        } catch (IOException e) {
            log.error("Error invalid address to redirect while create a bill", e);
            throw new ControllerException(e);
        }
    }
}
