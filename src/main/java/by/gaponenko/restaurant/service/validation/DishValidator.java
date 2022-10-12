package by.gaponenko.restaurant.service.validation;

import by.gaponenko.restaurant.bean.Dish;
import by.gaponenko.restaurant.bean.criteria.Criteria;
import by.gaponenko.restaurant.bean.criteria.SearchCriteria;

import java.math.BigDecimal;
import java.util.Map;

public class DishValidator {

    private static final DishValidator instance = new DishValidator();
    private static final ValidationHelper helper = ValidationHelper.getInstance();

    private DishValidator() {
    }

    public static void validate(Criteria criteria) throws ValidationException {
        if (criteria == null) {
            throw new ValidationException("Criteria to find User is null");
        }

        Map<String, Object> criterias = criteria.getCriteria();
        for (Map.Entry<String, Object> entry : criterias.entrySet()) {
            if (entry.getKey().equals(SearchCriteria.Dish.ID_DISH.name())) {
                helper.validateId(entry.getValue().toString());
            } else if (entry.getKey().equals(SearchCriteria.Dish.NAME.name())) {
                helper.validateName(entry.getValue().toString());
            } else if (entry.getKey().equals(SearchCriteria.Dish.PRICE.name())) {
                helper.validatePrice(entry.getValue().toString());
            } else if (entry.getKey().equals(SearchCriteria.Dish.ID_CATEGORY.name())) {
                helper.validateId(entry.getValue().toString());
            } else if (entry.getKey().equals(SearchCriteria.Dish.STATUS.name())) {
                helper.validateStatus(entry.getValue().toString());
            } else if (entry.getKey().equals(SearchCriteria.Dish.DESCRIPTION.name()) && entry.getValue() == null) {
                throw new ValidationException("Description of dish is empty!");
            }
        }
    }

    public static void validate(Dish dish) throws ValidationException {
        helper.validateId(Integer.toString(dish.getIdDish()));
       // helper.validateName(dish.getName());
        helper.validateDescription(dish.getDescription());
        helper.validatePrice(dish.getPrice().toString());
        helper.validatePhotoUrl(dish.getPhotoUrl());
    }


    public static DishValidator getInstance() {
        return instance;
    }
}
