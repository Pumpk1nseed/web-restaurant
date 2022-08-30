package by.gaponenko.restaurant.controller.command.impl;

import by.gaponenko.restaurant.controller.command.Command;
import by.gaponenko.restaurant.service.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.ParseException;

public class ChangeLocalizationCommand implements Command {

    public final Logger log = LoggerFactory.getLogger(this.getClass());
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException, ParseException, ServiceException {
        HttpSession session = req.getSession(true);
        session.setAttribute("localization", req.getParameter("localization"));

        String lastPage = (String) session.getAttribute("lastPage");
        try {
            req.getRequestDispatcher(lastPage).forward(req, resp);
        } catch (IOException e) {
            log.error("Invalid address to forward.", e);
            throw new ServletException(e);
        }
    }
}
