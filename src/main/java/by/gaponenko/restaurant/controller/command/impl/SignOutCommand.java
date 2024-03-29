package by.gaponenko.restaurant.controller.command.impl;

import by.gaponenko.restaurant.controller.ControllerException;
import by.gaponenko.restaurant.controller.JSPPageName;
import by.gaponenko.restaurant.controller.command.Command;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class SignOutCommand implements Command {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ControllerException {
        req.getSession().invalidate();
        req.getSession(true);

        try {
            resp.sendRedirect(JSPPageName.HOME_PAGE);
        } catch (IOException e){
            log.error("Invalid address to redirect while sign out");
            throw new ControllerException(e);
        }
    }
}
