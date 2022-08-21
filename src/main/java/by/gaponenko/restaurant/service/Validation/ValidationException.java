package by.gaponenko.restaurant.service.Validation;

import by.gaponenko.restaurant.service.ServiceException;

public class ValidationException extends ServiceException {

    public ValidationException(String message) {
        super(message);
    }

}
