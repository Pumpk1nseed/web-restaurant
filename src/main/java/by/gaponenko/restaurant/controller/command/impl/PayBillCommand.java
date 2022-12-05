package by.gaponenko.restaurant.controller.command.impl;

import by.gaponenko.restaurant.bean.User;
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

public class PayBillCommand implements Command {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private static final PaymentService paymentService = ServiceProvider.getInstance().getPaymentService();

    private static final String STATUS_PAID = "paid";

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ControllerException {
        int idBill = Integer.parseInt(req.getParameter(RequestParameterName.REQ_PARAM_ID_BILL));
        User currentUser = (User) req.getSession().getAttribute(RequestParameterName.REQ_PARAM_USER);
        int id_userRole = currentUser.getIdRole();

        try {
            paymentService.updateBillStatus(idBill, STATUS_PAID);
        } catch (ServiceException e) {
            log.error("Error while updating bill status", e);
            throw new ControllerException(e);
        }

        try {
            req.getSession().removeAttribute(RequestParameterName.REQ_PARAM_ORDER_FOR_BILL);

            if (id_userRole== 4) {
                resp.sendRedirect(JSPPageName.EDIT_SUCCESS_PAGE);
            } else if (id_userRole == 1) {
                resp.sendRedirect(JSPPageName.ORDER_FINISH_PAGE);
            }
        } catch (IOException e) {
            log.error("Error invalid address to redirect while updating bill status", e);
            throw new ControllerException(e);
        }
    }

}
