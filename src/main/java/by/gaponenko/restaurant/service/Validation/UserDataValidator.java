package by.gaponenko.restaurant.service.Validation;

public class UserDataValidator {

    //делаем класс сингтоном, мб не нужен сингтон
    //но валидатор не должен выбрасывать исключений,
    //т.к они говорят о том, что код некорректный
    private static final UserDataValidator instance = new UserDataValidator();

    private UserDataValidator(){}

    public boolean check(String login, String password) {
        //можно написать в файлу properties паттеры,
        //а валидатор будет поднимать из properties данные и проверять
        return true;
    }

    public static UserDataValidator getInstance(){
        return instance;
    }

}
