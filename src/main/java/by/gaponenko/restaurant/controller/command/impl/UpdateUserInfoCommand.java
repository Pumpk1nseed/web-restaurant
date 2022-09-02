package by.gaponenko.restaurant.controller.command.impl;

import by.gaponenko.restaurant.controller.ControllerException;
import by.gaponenko.restaurant.controller.command.Command;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class UpdateUserInfoCommand implements Command {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ControllerException {
    }
}
