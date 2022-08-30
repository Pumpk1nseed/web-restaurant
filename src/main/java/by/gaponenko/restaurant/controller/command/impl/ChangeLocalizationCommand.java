package by.gaponenko.restaurant.controller.command.impl;

import by.gaponenko.restaurant.controller.JSPPageName;
import by.gaponenko.restaurant.controller.RequestParameterName;
import by.gaponenko.restaurant.controller.command.Command;
import by.gaponenko.restaurant.service.ServiceException;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChangeLocalizationCommand implements Command {
    public final Logger log = LoggerFactory.getLogger(this.getClass());
    private static final String LAST_PAGE_ATTR = "lastPage";
    Pattern notPages = Pattern.compile("(?i)(\\W|^)(controller|images|css|js|ajaxController)(\\W|$)");
    Matcher mNotPages;
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException, ParseException, ServiceException {
        HttpSession session = req.getSession(true);

        String localization;
        localization = req.getParameter(RequestParameterName.REQ_PARAM_LOCALIZATION);

        session.setAttribute(RequestParameterName.REQ_PARAM_LOCALIZATION, localization);

        String lastPage = (String) session.getAttribute(LAST_PAGE_ATTR);

        mNotPages = notPages.matcher(lastPage);

        if (!mNotPages.find()) {
            lastPage = lastPage.replaceAll("web_restaurant_war/", "");
        }

        try {
            RequestDispatcher dispatcher = req.getRequestDispatcher(lastPage);
            dispatcher.forward(req, resp);
        } catch (IOException e) {
            log.error("Invalid address to forward.", e);
            throw new ServletException(e);
        }
    }
}
