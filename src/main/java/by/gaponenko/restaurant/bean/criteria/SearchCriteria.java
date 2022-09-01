package by.gaponenko.restaurant.bean.criteria;

public final class SearchCriteria {
    private SearchCriteria() {
    }

    public static enum Order {
        ID_ORDER, ID_USER, STATUS
    }

    public static enum Dish {
        ID_DISH, NAME, DESCRIPTION, PRICE, STATUS, ID_CATEGORY
    }

    public static enum User {
        LOGIN, ID_USER
    }
}
