package by.gaponenko.restaurant.controller.command;

import by.gaponenko.restaurant.dao.DaoException;
import by.gaponenko.restaurant.service.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.text.ParseException;

public interface Command {

    void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException, ParseException, ServiceException, DaoException;
}
