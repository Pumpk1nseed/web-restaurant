package by.gaponenko.restaurant.service.Validation;

import by.gaponenko.restaurant.bean.Order;

public class OrderValidator {

    private static final OrderValidator instance = new OrderValidator();
    private OrderValidator(){}

    public boolean validate(Order order) {
        //можно написать в файле properties паттеры,
        //а валидатор будет поднимать из properties данные и проверять
        return true;
    }

    public boolean validate(int idUser) {
        return true;
    }

    public boolean validate(int idOrder, int idDish, Integer quantity){
        return true;
    }

    public static OrderValidator getInstance(){
        return instance;
    }

}
