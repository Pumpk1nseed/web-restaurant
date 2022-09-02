package by.gaponenko.restaurant.controller.command;

import by.gaponenko.restaurant.controller.ControllerException;
import by.gaponenko.restaurant.service.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface Command {

    void execute(HttpServletRequest req, HttpServletResponse resp) throws ControllerException;
}
