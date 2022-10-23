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

public class EditUserCommand implements Command {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    UserService userService = ServiceProvider.getInstance().getUserService();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ControllerException {
        String userName = req.getParameter(RequestParameterName.REQ_PARAM_NAME);
        String surName = req.getParameter(RequestParameterName.REQ_PARAM_SURNAME);
        String lastName = req.getParameter(RequestParameterName.REQ_PARAM_LAST_NAME);
        String telephoneNumber = req.getParameter(RequestParameterName.REQ_PARAM_PHONE);
        String email = req.getParameter(RequestParameterName.REQ_PARAM_EMAIL);
        String address = req.getParameter(RequestParameterName.REQ_PARAM_ADDRESS);
        String newPassword = req.getParameter(RequestParameterName.REQ_PARAM_PASSWORD);
        String role = req.getParameter(RequestParameterName.REQ_PARAM_ROLE);
        String login = req.getParameter(RequestParameterName.REQ_PARAM_LOGIN);
        int idUser = Integer.parseInt(req.getParameter(RequestParameterName.REQ_PARAM_ID_EDITED_USER));

        RegistrationUserData newUserData = new RegistrationUserData();
        newUserData.setIdUser(idUser);
        newUserData.setLogin(login);
        newUserData.setName(userName);
        newUserData.setSurname(surName);
        newUserData.setLastName(lastName);
        newUserData.setPassword(newPassword);
        newUserData.setRole(role);
        newUserData.setTelephoneNumber(telephoneNumber);
        newUserData.setAddress(address);
        newUserData.setEmail(email);

        try {
            userService.updateUserData(newUserData);
            resp.sendRedirect(JSPPageName.EDIT_SUCCESS_PAGE);

        } catch (IOException e) {
            log.error("Invalid address to redirect while updating user data", e);
            throw new ControllerException(e);
        } catch (ServiceException e) {
            log.error("Error occurred while updating user data", e);
            throw new ControllerException(e.getMessage());
        }
    }
}
