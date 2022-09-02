package by.gaponenko.restaurant.controller.command.impl;

import by.gaponenko.restaurant.controller.JSPPageName;
import by.gaponenko.restaurant.controller.command.Command;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class NoSuchCommand implements Command {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) {
        try {
            RequestDispatcher requestDispatcher = req.getRequestDispatcher(JSPPageName.ERROR_PAGE);
            requestDispatcher.forward(req, resp);
        } catch (IOException e) {
            log.error("Invalid address getRequestDispatcher(/errorPage) in no such command..", e);
        } catch (ServletException e) {
            log.error("Error to forward in no such command..", e);
        }
    }
}
