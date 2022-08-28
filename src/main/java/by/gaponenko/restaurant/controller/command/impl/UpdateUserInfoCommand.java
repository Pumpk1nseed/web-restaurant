package by.gaponenko.restaurant.controller.command.impl;

import by.gaponenko.restaurant.controller.command.Command;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.text.ParseException;

public class UpdateUserInfoCommand implements Command {

    //private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException, ParseException {

        return null;
    }
}
