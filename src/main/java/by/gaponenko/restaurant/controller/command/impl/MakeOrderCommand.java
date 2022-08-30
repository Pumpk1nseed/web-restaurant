package by.gaponenko.restaurant.controller.command.impl;

import by.gaponenko.restaurant.controller.command.Command;
import by.gaponenko.restaurant.service.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.text.ParseException;

public class MakeOrderCommand implements Command {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException, ParseException, ServiceException {

    }
}
