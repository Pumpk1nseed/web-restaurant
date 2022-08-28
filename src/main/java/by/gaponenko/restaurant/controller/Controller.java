package by.gaponenko.restaurant.controller;

import by.gaponenko.restaurant.controller.command.Command;
import by.gaponenko.restaurant.controller.command.CommandHelper;
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
    private static final long serialVersionUID = 1L;
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private static final CommandHelper helper = new CommandHelper();

    public Controller() {
        super();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

/*        String commandName;
        Command command;
        String page = null;

        commandName = req.getParameter(RequestParameterName.REQ_PARAM_COMAND_NAME);
        command = helper.getCommand(commandName);

        //доработать
        try {
            command.execute(req, resp);
        } catch (ParseException | ServiceException e) {
            throw new RuntimeException(e);
        }*/

        processRequest(req, resp);

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /*super.doGet(req, resp);*/
        processRequest(req, resp);
    }

    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, java.io.IOException {
        HttpSession session = req.getSession(true);

        String commandName;
        Command command;
        String page;

        commandName = req.getParameter(RequestParameterName.REQ_PARAM_COMAND_NAME);
        command = helper.getCommand(commandName);

        //доработать
        try {
            page = command.execute(req, resp);

            switch (req.getMethod()) {
                case "GET" -> dispatch(req, resp, page);
                case "POST" -> resp.sendRedirect("controller?command=forward_command&target=" + page);
            }
        } catch (ParseException | ServiceException e) {
            throw new RuntimeException(e);
        } catch (NullPointerException e) {
            log.error("Null command name", e);
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
