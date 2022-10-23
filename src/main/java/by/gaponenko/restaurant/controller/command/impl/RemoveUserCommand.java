package by.gaponenko.restaurant.controller.command.impl;

import by.gaponenko.restaurant.bean.RegistrationUserData;
import by.gaponenko.restaurant.bean.criteria.Criteria;
import by.gaponenko.restaurant.bean.criteria.SearchCriteria;
import by.gaponenko.restaurant.controller.ControllerException;
import by.gaponenko.restaurant.controller.JSPPageName;
import by.gaponenko.restaurant.controller.RequestParameterName;
import by.gaponenko.restaurant.controller.command.Command;
import by.gaponenko.restaurant.service.ServiceException;
import by.gaponenko.restaurant.service.ServiceProvider;
import by.gaponenko.restaurant.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

public class RemoveUserCommand implements Command {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private static final UserService userService = ServiceProvider.getInstance().getUserService();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ControllerException {
        int idUserToRemove = Integer.parseInt(req.getParameter(RequestParameterName.REQ_PARAM_ID_USER_TO_REMOVE));

        try {
            removeUserFromDB(idUserToRemove);

        } catch (ServiceException e) {
            log.error("Error occurred while trying to remove user", e);
            throw new ControllerException(e);
        }

        removeUserFromSession(req, idUserToRemove);

        try {
            resp.sendRedirect(JSPPageName.EDIT_SUCCESS_PAGE);
        } catch (IOException e) {
            log.error("Error invalid address to redirect while remove user", e);
            throw new ControllerException(e);
        }
    }

    private void removeUserFromDB(int idUserToRemove) throws ServiceException {
        Criteria criteria = new Criteria();
        criteria.add(SearchCriteria.User.ID_USER.toString(), idUserToRemove);

        userService.removeUser(criteria);
    }

    private void removeUserFromSession(HttpServletRequest req, int idUserToRemove) {
        HttpSession session = req.getSession();
        List<RegistrationUserData> users = (List<RegistrationUserData>) session.getAttribute(RequestParameterName.REQ_PARAM_USERS);

        for (RegistrationUserData userData: users) {
            if (userData.getIdUser() == idUserToRemove) {
                users.remove(userData);
                break;
            }
        }
    }
}
