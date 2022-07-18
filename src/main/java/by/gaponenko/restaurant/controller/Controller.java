package by.gaponenko.restaurant.controller;

import by.gaponenko.restaurant.controller.command.Command;
import by.gaponenko.restaurant.controller.command.CommandHelper;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class Controller extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final CommandHelper helper = new CommandHelper();

    public Controller() {
        super();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String commandName;
        Command command;
        String page = null;

        commandName = req.getParameter(RequestParameterName.REQ_PARAM_COMAND_NAME);
        command = helper.getCommand(commandName);

        command.execute(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }
}
