package by.gaponenko.restaurant.service.Validation;

import by.gaponenko.restaurant.service.ServiceException;

public class ValidationException extends ServiceException {
        public ValidationException() {
            super();
        }

        public ValidationException(String message) {
            super(message);
        }

        public ValidationException(String message, Throwable cause) {
            super(message, cause);
        }

        public ValidationException(Throwable cause) {
            super(cause);
        }
}
