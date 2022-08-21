package by.gaponenko.restaurant.controller.command.impl;

import by.gaponenko.restaurant.bean.RegistrationUserData;
import by.gaponenko.restaurant.bean.User;
import by.gaponenko.restaurant.controller.RequestParameterName;
import by.gaponenko.restaurant.controller.command.Command;
import by.gaponenko.restaurant.service.ServiceException;
import by.gaponenko.restaurant.service.ServiceProvider;
import by.gaponenko.restaurant.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RegistrationCommand implements Command {

    SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");

    private static final String AUTH_PAGE = "jspauthorization";
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException, ParseException {

        RegistrationUserData userData =
                new RegistrationUserData(
                        req.getParameter(RequestParameterName.REQ_PARAM_LOGIN),
                        req.getParameter(RequestParameterName.REQ_PARAM_PASSWORD).toCharArray(),
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

            req.getSession().setAttribute("user", new User(userData.getName(), userData.getLogin(), userData.getRole()));

            resp.sendRedirect(AUTH_PAGE);
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
    }
}
