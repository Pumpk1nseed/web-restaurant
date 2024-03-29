package by.gaponenko.restaurant.controller.command.impl;

import by.gaponenko.restaurant.bean.RegistrationUserData;
import by.gaponenko.restaurant.bean.User;
import by.gaponenko.restaurant.controller.ControllerException;
import by.gaponenko.restaurant.controller.JSPPageName;
import by.gaponenko.restaurant.controller.RequestParameterName;
import by.gaponenko.restaurant.controller.command.Command;
import by.gaponenko.restaurant.service.ServiceException;
import by.gaponenko.restaurant.service.ServiceProvider;
import by.gaponenko.restaurant.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class RegistrationCommand implements Command {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ControllerException {

        RegistrationUserData userData =
                new RegistrationUserData(
                        req.getParameter(RequestParameterName.REQ_PARAM_LOGIN),
                        req.getParameter(RequestParameterName.REQ_PARAM_PASSWORD),
                        req.getParameter(RequestParameterName.REQ_PARAM_NAME),
                        req.getParameter(RequestParameterName.REQ_PARAM_SURNAME),
                        req.getParameter(RequestParameterName.REQ_PARAM_LAST_NAME),
                        req.getParameter(RequestParameterName.REQ_PARAM_DATE_BIRTH),
                        req.getParameter(RequestParameterName.REQ_PARAM_PHONE),
                        req.getParameter(RequestParameterName.REQ_PARAM_EMAIL),
                        req.getParameter(RequestParameterName.REQ_PARAM_ADDRESS),
                        req.getParameter(RequestParameterName.REQ_PARAM_ROLE));

        UserService userService = ServiceProvider.getInstance().getUserService();

        try {
            userService.registration(userData);

            req.getSession().setAttribute(RequestParameterName.REQ_PARAM_USER, new User(userData.getLogin(), userData.getPassword()));

            resp.sendRedirect(JSPPageName.USER_AUTHORIZATION_PAGE);
        } catch (IOException e ) {
            log.error("Invalid address to forward in registration command", e);
            throw new ControllerException(e);
        } catch (ServiceException e){
            log.error("Error occurred while registration", e);
            throw new ControllerException(e);
        }
    }
}

