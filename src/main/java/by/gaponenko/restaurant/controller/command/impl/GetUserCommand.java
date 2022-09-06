package by.gaponenko.restaurant.controller.command.impl;

import by.gaponenko.restaurant.bean.DishCategory;
import by.gaponenko.restaurant.bean.RegistrationUserData;
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
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

public class GetUserCommand implements Command {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    UserService userService = ServiceProvider.getInstance().getUserService();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ControllerException {
        try {
            List<RegistrationUserData> usersData = userService.getUsers();

            HttpSession session = req.getSession();
            session.setAttribute(RequestParameterName.REQ_PARAM_USERS, usersData);

            RequestDispatcher requestDispatcher = req.getRequestDispatcher(JSPPageName.USERS_LIST_PAGE);
            requestDispatcher.forward(req, resp);

        } catch (IOException e) {
            log.error("Invalid address to forward in get Users list command", e);
            throw new ControllerException(e);
        } catch (ServletException | ServiceException e) {
            log.error("Error occurred in get Users list command", e);
            throw new ControllerException(e);
        }
    }
}
