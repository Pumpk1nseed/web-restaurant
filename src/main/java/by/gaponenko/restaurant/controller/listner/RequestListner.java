package by.gaponenko.restaurant.controller.listner;

import by.gaponenko.restaurant.controller.JSPPageName;
import by.gaponenko.restaurant.controller.RequestParameterName;
import jakarta.servlet.ServletRequestEvent;
import jakarta.servlet.ServletRequestListener;
import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebListener
public class RequestListner implements ServletRequestListener {
    Pattern notPages = Pattern.compile("(?i)(\\W|^)(controller|images|css|js|ajaxController)(\\W|$)");
    Matcher mNotPages;
    private static final String LAST_PAGE_ATTR = "lastPage";

    @Override
    public void requestDestroyed(ServletRequestEvent sre) {
        HttpServletRequest request = (HttpServletRequest) sre.getServletRequest();
        HttpSession session = request.getSession();

        String lastPage = (String) session.getAttribute(LAST_PAGE_ATTR);
        if (lastPage == null) {
            session.setAttribute(LAST_PAGE_ATTR, JSPPageName.HOME_PAGE);
        } else {
            String uri = request.getRequestURI();

            mNotPages = notPages.matcher(uri);

            if (!mNotPages.find()) {
                session.setAttribute(LAST_PAGE_ATTR, uri);
            } else{
                lastPage = lastPage.replaceAll("web_restaurant_war/", "");
                session.setAttribute(LAST_PAGE_ATTR, lastPage);
            }
        }
    }

    @Override
    public void requestInitialized(ServletRequestEvent sre) {
    }
}