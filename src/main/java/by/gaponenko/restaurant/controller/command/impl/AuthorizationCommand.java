package by.gaponenko.restaurant.controller.command.impl;

import by.gaponenko.restaurant.bean.User;
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

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;

public class AuthorizationCommand implements Command {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

        String login;
        String password;

        login = req.getParameter(RequestParameterName.REQ_PARAM_LOGIN);
        password = req.getParameter(RequestParameterName.REQ_PARAM_PASSWORD);

        UserService userService = ServiceProvider.getInstance().getUserService();

        User user;
        try {
            user = userService.authorization(login, password);

            req.setAttribute("user", user);

            RequestDispatcher dispatcher = req.getRequestDispatcher(JSPPageName.USER_AUTH_PAGE);
            dispatcher.forward(req, resp);

        } catch (ServiceException e) {
            //исправить
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ServletException e) {
            throw new RuntimeException(e);
        }
    }
}
