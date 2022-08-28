package by.gaponenko.restaurant.controller.command.impl;

import by.gaponenko.restaurant.controller.command.Command;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class NoSuchCommand implements Command {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("No such command");
        return null;
    }
}
