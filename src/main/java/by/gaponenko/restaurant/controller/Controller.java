package by.gaponenko.restaurant.controller;

import by.gaponenko.restaurant.controller.command.Command;
import by.gaponenko.restaurant.controller.command.CommandHelper;
import by.gaponenko.restaurant.dao.DaoException;
import by.gaponenko.restaurant.service.ServiceException;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.IOException;
import java.text.ParseException;

public class Controller extends HttpServlet {
    private static final long serialVersionUID = 2133400551086625355L;
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private static final CommandHelper helper = new CommandHelper();

    public Controller() {
        super();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, java.io.IOException {
        HttpSession session = req.getSession(true);

        String commandName;
        Command command;

        commandName = req.getParameter(RequestParameterName.REQ_PARAM_COMAND_NAME);
        command = helper.getCommand(commandName);

        try {
            command.execute(req, resp);

        } catch (ControllerException e) {
            log.error(e.getMessage(), e);

            int lastIndx = e.getMessage().lastIndexOf(":");
            String errorMsg;
            if (lastIndx == -1) {
                errorMsg = e.getMessage();
            } else {
                errorMsg = e.getMessage().substring(lastIndx);
            }
            req.getSession().setAttribute(RequestParameterName.REQ_PARAM_ERROR_MSG, errorMsg);
            dispatch(req, resp, JSPPageName.ERROR_PAGE);

        } catch (NullPointerException e) {
            log.error("Null command name", e);
            String errorMsg = e.getMessage();
            req.getSession().setAttribute(RequestParameterName.REQ_PARAM_ERROR_MSG, errorMsg);
            dispatch(req, resp, JSPPageName.ERROR_PAGE);
        }
    }

    private void dispatch(HttpServletRequest req, HttpServletResponse resp, String page) {
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);

        try {
            dispatcher.forward(req, resp);
        } catch (NullPointerException e) {
            log.error("Null page", e);
            dispatch(req, resp, JSPPageName.ERROR_PAGE);
        } catch (ServletException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
