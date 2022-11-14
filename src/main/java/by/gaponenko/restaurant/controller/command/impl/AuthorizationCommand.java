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
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class AuthorizationCommand implements Command {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ControllerException {

        String login;
        String password;

        login = req.getParameter(RequestParameterName.REQ_PARAM_LOGIN);
        password = req.getParameter(RequestParameterName.REQ_PARAM_PASSWORD);

        UserService userService = ServiceProvider.getInstance().getUserService();

        User user;
        RegistrationUserData userData;
        try {
            user = userService.authorization(login, password);
            userData = userService.loadUserDataByLogin(login);

            req.getSession().setAttribute(RequestParameterName.REQ_PARAM_USER, user);
            req.getSession().setAttribute(RequestParameterName.REQ_PARAM_USER_DATA, userData);

            RequestDispatcher dispatcher = req.getRequestDispatcher(JSPPageName.USER_AUTHORIZED_PAGE);
            dispatcher.forward(req, resp);

        } catch (IOException e ) {
            log.error("Invalid address to forward in authorization command", e);
            throw new ControllerException(e);
        } catch (ServiceException | ServletException e){
            log.error("Error occurred while authorization", e);
            throw new ControllerException(e);
        }
    }
}
