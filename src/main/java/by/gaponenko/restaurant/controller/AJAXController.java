package by.gaponenko.restaurant.controller;

import by.gaponenko.restaurant.controller.command.Command;
import by.gaponenko.restaurant.controller.command.CommandHelper;
import by.gaponenko.restaurant.dao.DaoException;
import by.gaponenko.restaurant.service.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@WebServlet("/ajaxController")
public class AJAXController extends HttpServlet {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private static final CommandHelper helper = CommandHelper.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String commandName;
        Command command;

        commandName = req.getParameter(RequestParameterName.REQ_PARAM_COMAND_NAME);
        command = helper.getCommand(commandName);

        try {
            command.execute(req, resp);
        } catch (ControllerException e) {
            log.error(e.getMessage(), e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        doGet(req, resp);
    }
}
