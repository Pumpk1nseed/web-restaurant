package by.gaponenko.restaurant.service.Validation;

import by.gaponenko.restaurant.bean.RegistrationUserData;
import by.gaponenko.restaurant.bean.criteria.Criteria;
import by.gaponenko.restaurant.bean.criteria.SearchCriteria;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//но валидатор не должен выбрасывать исключений,
//т.к они говорят о том, что код некорректный
public class UserDataValidator {

    private static final UserDataValidator instance = new UserDataValidator();

    private static final ValidationHelper helper = ValidationHelper.getInstance();

    public void validateUserData(RegistrationUserData userData) throws ValidationException {

        if (userData == null) {
            throw new ValidationException("User is null");
        } else {
            helper.validateName(userData.getName());
            helper.validateSurName(userData.getSurname());
            helper.validateLastName(userData.getLastName());
            helper.validateLogin(userData.getLogin());
            helper.validateTelephone(userData.getTelephoneNumber());
            helper.validateEmail(userData.getEmail());
            helper.validateRole(userData.getRole());
        }
    }

    public void validate(Criteria criteria) throws ValidationException {
        if (criteria == null) {
            throw new ValidationException("Criteria to find User is null");
        }

        Map<String, Object> criterias = criteria.getCriteria();
        for (Map.Entry<String, Object> entry : criterias.entrySet()) {
            if (entry.getKey().equals(SearchCriteria.User.LOGIN.name())) {
                helper.validateLogin(entry.getValue().toString());
            } else if (entry.getKey().equals(SearchCriteria.User.NAME.name())) {
                helper.validateName(entry.getValue().toString());
            } else if (entry.getKey().equals(SearchCriteria.User.SURNAME.name())) {
                helper.validateSurName(entry.getValue().toString());
            } else if (entry.getKey().equals(SearchCriteria.User.LAST_NAME.name())) {
                helper.validateLastName(entry.getValue().toString());
            } else if (entry.getKey().equals(SearchCriteria.User.TELEPHONE_NUMBER.name())) {
                helper.validateTelephone(entry.getValue().toString());
            } else if (entry.getKey().equals(SearchCriteria.User.EMAIL.name())) {
                helper.validateEmail(entry.getValue().toString());
            }
        }
    }

    public static UserDataValidator getInstance() {
        return instance;
    }

}
