package by.gaponenko.restaurant.bean.criteria;

/**
 * The ${@code SearchCriteria} class provides possible options for creating a {@link Criteria} class
 * @see Criteria
 */
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
        LOGIN, ID_USER, PASSWORD, ROLE, STATUS, EMAIL, TELEPHONE_NUMBER, NAME, SURNAME, LAST_NAME
    }
}
