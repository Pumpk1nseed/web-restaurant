package by.gaponenko.restaurant.controller.command;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.text.ParseException;

public interface Command {

    void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException, ParseException;
}
