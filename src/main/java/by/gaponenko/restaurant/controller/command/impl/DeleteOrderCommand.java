package by.gaponenko.restaurant.controller.command.impl;

import by.gaponenko.restaurant.controller.ControllerException;
import by.gaponenko.restaurant.controller.JSPPageName;
import by.gaponenko.restaurant.controller.RequestParameterName;
import by.gaponenko.restaurant.controller.command.Command;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class DeleteOrderCommand implements Command {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ControllerException {
        req.getSession().removeAttribute(RequestParameterName.REQ_PARAM_ORDER);
        req.getSession().removeAttribute(RequestParameterName.REQ_PARAM_QUANTITY_OF_DISHES);

        try {
            resp.sendRedirect(JSPPageName.BASKET_PAGE);
        } catch (IOException e) {
            log.error("Error while delete an order: invalid address to redirect");
            throw new ControllerException(e);
        }
    }
}
