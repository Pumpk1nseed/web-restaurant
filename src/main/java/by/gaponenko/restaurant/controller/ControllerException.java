package by.gaponenko.restaurant.controller;

public class ControllerException extends Exception{
    private static final long serialVersionUID = 2585357278908575546L;

    public ControllerException() {
    }

    public ControllerException(final String message) {
        super(message);
    }

    public ControllerException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public ControllerException(final Throwable cause) {
        super(cause);
    }
}
