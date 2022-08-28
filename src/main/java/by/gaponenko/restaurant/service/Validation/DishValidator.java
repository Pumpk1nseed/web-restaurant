package by.gaponenko.restaurant.service.Validation;

import by.gaponenko.restaurant.bean.Dish;
import by.gaponenko.restaurant.bean.Menu;

public class DishValidator {

    private static final DishValidator instance = new DishValidator();

    private DishValidator(){}

    public boolean validate(Menu menu) {
        //можно написать в файлу properties паттеры,
        //а валидатор будет поднимать из properties данные и проверять
        return true;
    }
    public boolean validateDish(Dish dish) {
        //можно написать в файлу properties паттеры,
        //а валидатор будет поднимать из properties данные и проверять
        return true;
    }


    public static DishValidator getInstance(){
        return instance;
    }
}
