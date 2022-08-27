package by.gaponenko.restaurant.controller.command.impl;

import by.gaponenko.restaurant.controller.command.Command;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class NoSuchCommand implements Command {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("No such command");
    }
}
