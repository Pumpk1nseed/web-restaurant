package by.gaponenko.restaurant.service.Validation;

import by.gaponenko.restaurant.bean.RegistrationUserData;
import by.gaponenko.restaurant.bean.criteria.Criteria;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

//но валидатор не должен выбрасывать исключений,
//т.к они говорят о том, что код некорректный
public class UserDataValidator {

    private final String login;
    private final String name;
    private final String email;
    private final String telephone;
    private final String role;

    private final String loginErrorMsg;
    private final String userErrorMsg;
    private final String nameErrorMsg;
    private final String emailErrorMsg;
    private final String telephoneErrorMsg;
    private final String roleErrorMsg;
    private final String nullErrorMsg;
    private static final UserDataValidator instance = new UserDataValidator();
    ValidationResourceManager validationResourceManager = new ValidationResourceManager();

    Pattern pattern;
    Matcher matcher;

    private UserDataValidator() {
        this.login = validationResourceManager.getValue(ValidatorParameter.VALIDATION_REGEX_LOGIN);
        this.name = validationResourceManager.getValue(ValidatorParameter.VALIDATION_REGEX_NAME);
        this.email = validationResourceManager.getValue(ValidatorParameter.VALIDATION_REGEX_EMAIL);
        this.telephone = validationResourceManager.getValue(ValidatorParameter.VALIDATION_REGEX_TELEPHONE);
        this.role = validationResourceManager.getValue(ValidatorParameter.VALIDATION_REGEX_ROLE);

        this.loginErrorMsg = validationResourceManager.getValue(ValidatorParameter.VALIDATION_ERRORMSG_LOGIN);
        this.userErrorMsg = validationResourceManager.getValue(ValidatorParameter.VALIDATION_ERRORMSG_USER);
        this.nameErrorMsg = validationResourceManager.getValue(ValidatorParameter.VALIDATION_ERRORMSG_NAME);
        this.emailErrorMsg = validationResourceManager.getValue(ValidatorParameter.VALIDATION_ERRORMSG_EMAIL);
        this.telephoneErrorMsg = validationResourceManager.getValue(ValidatorParameter.VALIDATION_ERRORMSG_TELEPHONE);
        this.roleErrorMsg = validationResourceManager.getValue(ValidatorParameter.VALIDATION_ERRORMSG_ROLE);
        this.nullErrorMsg = validationResourceManager.getValue(ValidatorParameter.VALIDATION_ERRORMSG_NULL);
    }

    public void validateUserData(RegistrationUserData userData) throws ValidationException {

        if (userData == null) {
            throw new ValidationException(userErrorMsg);
        } else {
            validateName(userData.getName());
            validateSurName(userData.getSurname());
            validateLastName(userData.getLastName());
            validateLogin(userData.getLogin());
            validateTelephone(userData.getTelephoneNumber());
            validateEmail(userData.getEmail());
            validateRole(userData.getRole());
        }
    }

    private void validateName(String userName) throws ValidationException {
        if (userName != null) {
            pattern = pattern.compile(name);
            matcher = pattern.matcher(userName);

            if (matcher.find() || userName.length() > 50) {
                throw new ValidationException(nameErrorMsg);
            }
        } else {
            throw new ValidationException(nullErrorMsg);
        }
    }

    private void validateSurName(String surName) throws ValidationException {
        if (surName != null) {
            pattern = pattern.compile(name);
            matcher = pattern.matcher(surName);

            if (matcher.find() || surName.length() > 50) {
                throw new ValidationException(nameErrorMsg);
            }
        } else {
            throw new ValidationException(nullErrorMsg);
        }
    }

    private void validateLastName(String lastName) throws ValidationException {
        if (lastName != null) {
            pattern = pattern.compile(name);
            matcher = pattern.matcher(lastName);

            if (matcher.find() || lastName.length() > 50) {
                throw new ValidationException(nameErrorMsg);
            }
        }
    }

    private void validateLogin(String userLogin) throws ValidationException {
        if (userLogin != null) {
            pattern = pattern.compile(login);
            matcher = pattern.matcher(userLogin);

            if (!matcher.find() || userLogin.length() > 10) {
                throw new ValidationException(loginErrorMsg);
            }
        } else {
            throw new ValidationException(nullErrorMsg);
        }
    }

    private void validateTelephone(String userTelephone) throws ValidationException {
        if (userTelephone != null) {
            pattern = pattern.compile(telephone);
            matcher = pattern.matcher(userTelephone);

            if (matcher.find()|| (userTelephone.length() < 10 || userTelephone.length() > 14)) {
                throw new ValidationException(telephoneErrorMsg);
            }
        } else {
            throw new ValidationException(nullErrorMsg);
        }
    }

    private void validateRole(String userRole) throws ValidationException {
        if (userRole != null) {
            pattern = pattern.compile(role);
            matcher = pattern.matcher(userRole);

            if (!matcher.find()) {
                throw new ValidationException(roleErrorMsg);
            }
        } else {
            throw new ValidationException(nullErrorMsg);
        }
    }


    private void validateEmail(String userEmail) throws ValidationException {
        if (userEmail != null) {
            pattern = pattern.compile(email);
            matcher = pattern.matcher(userEmail);

            if (!matcher.find()) {
                throw new ValidationException(emailErrorMsg);
            }
        }
    }


    public static void validate(Criteria criteria) throws ValidationException {
    }

    public static UserDataValidator getInstance() {
        return instance;
    }

}
