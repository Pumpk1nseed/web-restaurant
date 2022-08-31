package by.gaponenko.restaurant.service.Validation;

import by.gaponenko.restaurant.bean.Dish;
import by.gaponenko.restaurant.bean.Menu;
import by.gaponenko.restaurant.bean.criteria.Criteria;

public class DishValidator {

    private static final DishValidator instance = new DishValidator();

    private DishValidator(){}

    public boolean validate(Menu menu) {
        //можно написать в файле properties паттеры,
        //а валидатор будет поднимать из properties данные и проверять
        return true;
    }
    public boolean validateDish(Dish dish) {
        //можно написать в файле properties паттеры,
        //а валидатор будет поднимать из properties данные и проверять
        return true;
    }

    public static void validate(Criteria criteria) throws ValidationException {
        //мб прописать валидатор в criteria
    }


    public static DishValidator getInstance(){
        return instance;
    }
}
