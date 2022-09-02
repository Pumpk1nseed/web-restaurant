package by.gaponenko.restaurant.bean.criteria;

import by.gaponenko.restaurant.bean.Dish;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

public final class SearchCriteria {
    private SearchCriteria() {
    }

    public static enum Order {
        ID_ORDER, ID_USER, STATUS, DAYTIME, PRICE
    }

    public static enum Dish {
        ID_DISH, NAME, DESCRIPTION, PRICE, STATUS, ID_CATEGORY
    }

    public static enum User {
        LOGIN, ID_USER, PASSWORD, IDROLE, STATUS
    }
}
