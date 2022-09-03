package by.gaponenko.restaurant.service.Validation;

import by.gaponenko.restaurant.dao.pool.DBResourceManager;

import java.util.ResourceBundle;

public class ValidationResourceManager {
    private final static ValidationResourceManager instance = new ValidationResourceManager();

    private ResourceBundle bundle = ResourceBundle.getBundle("validation");

    public static ValidationResourceManager getInstance() {
        return instance;
    }

    public String getValue(String key) {
        return bundle.getString(key);
    }
}
