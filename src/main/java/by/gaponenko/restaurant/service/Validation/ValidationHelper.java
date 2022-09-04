package by.gaponenko.restaurant.service.Validation;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationHelper {
    private final String login;
    private final String name;
    private final String email;
    private final String telephone;
    private final String role;
    private final String id;
    private final String price;
    private final String status;

    private final String loginErrorMsg;
    private final String nameErrorMsg;
    private final String emailErrorMsg;
    private final String telephoneErrorMsg;
    private final String roleErrorMsg;
    private final String nullErrorMsg;
    private final String idErrorMsg;
    private final String priceErrorMsg;
    private final String statusErrorMsg;
    private static final ValidationHelper instance = new ValidationHelper();
    ValidationResourceManager validationResourceManager = new ValidationResourceManager();

    Pattern pattern;
    Matcher matcher;

    private ValidationHelper() {
        this.login = validationResourceManager.getValue(ValidationParameter.VALIDATION_REGEX_LOGIN);
        this.name = validationResourceManager.getValue(ValidationParameter.VALIDATION_REGEX_NAME);
        this.email = validationResourceManager.getValue(ValidationParameter.VALIDATION_REGEX_EMAIL);
        this.telephone = validationResourceManager.getValue(ValidationParameter.VALIDATION_REGEX_TELEPHONE);
        this.role = validationResourceManager.getValue(ValidationParameter.VALIDATION_REGEX_ROLE);
        this.id = validationResourceManager.getValue(ValidationParameter.VALIDATION_REGEX_ID);
        this.price = validationResourceManager.getValue(ValidationParameter.VALIDATION_REGEX_PRICE);
        this.status = validationResourceManager.getValue(ValidationParameter.VALIDATION_REGEX_STATUS);

        this.loginErrorMsg = validationResourceManager.getValue(ValidationParameter.VALIDATION_ERRORMSG_LOGIN);
        this.nameErrorMsg = validationResourceManager.getValue(ValidationParameter.VALIDATION_ERRORMSG_NAME);
        this.emailErrorMsg = validationResourceManager.getValue(ValidationParameter.VALIDATION_ERRORMSG_EMAIL);
        this.telephoneErrorMsg = validationResourceManager.getValue(ValidationParameter.VALIDATION_ERRORMSG_TELEPHONE);
        this.roleErrorMsg = validationResourceManager.getValue(ValidationParameter.VALIDATION_ERRORMSG_ROLE);
        this.nullErrorMsg = validationResourceManager.getValue(ValidationParameter.VALIDATION_ERRORMSG_NULL);
        this.idErrorMsg = validationResourceManager.getValue(ValidationParameter.VALIDATION_ERRORMSG_ID);
        this.priceErrorMsg = validationResourceManager.getValue(ValidationParameter.VALIDATION_ERRORMSG_PRICE);
        this.statusErrorMsg = validationResourceManager.getValue(ValidationParameter.VALIDATION_ERRORMSG_STATUS);
    }

    public void validateName(String nameInput) throws ValidationException {
        if (nameInput != "" && nameInput != null) {
            pattern = pattern.compile(name);
            matcher = pattern.matcher(nameInput);

            if (matcher.find() || nameInput.length() > 50) {
                throw new ValidationException(nameErrorMsg);
            }
        }
    }

    public void validateSurName(String surName) throws ValidationException {
        if (surName != "" && surName != null) {
            pattern = pattern.compile(name);
            matcher = pattern.matcher(surName);

            if (matcher.find() || surName.length() > 50) {
                throw new ValidationException(nameErrorMsg);
            }
        }
    }

    public void validateLastName(String lastName) throws ValidationException {
        if (lastName !="" && lastName != null) {
            pattern = pattern.compile(name);
            matcher = pattern.matcher(lastName);

            if (matcher.find() || lastName.length() > 50) {
                throw new ValidationException(nameErrorMsg);
            }
        }
    }

    public void validateLogin(String userLogin) throws ValidationException {
        if (userLogin != "" && userLogin != null) {
            pattern = pattern.compile(login);
            matcher = pattern.matcher(userLogin);

            if (!matcher.find() || userLogin.length() > 15) {
                throw new ValidationException(loginErrorMsg);
            }
        }
    }

    public void validateTelephone(String userTelephone) throws ValidationException {
        if (userTelephone != "" && userTelephone != null) {
            pattern = pattern.compile(telephone);
            matcher = pattern.matcher(userTelephone);

            if (matcher.find() || (userTelephone.length() < 10 || userTelephone.length() > 14)) {
                throw new ValidationException(telephoneErrorMsg);
            }
        }
    }

    public void validateRole(String roleInput) throws ValidationException {
        if (roleInput != "" && roleInput != null) {
            pattern = pattern.compile(role);
            matcher = pattern.matcher(roleInput);
            System.out.println(roleInput);
            if (!matcher.find()) {
                throw new ValidationException(roleErrorMsg);
            }
        }
    }

    public void validateEmail(String emailInput) throws ValidationException {
        if (emailInput != "" && emailInput != null) {
            pattern = pattern.compile(email);
            matcher = pattern.matcher(emailInput);

            if (!matcher.find()) {
                throw new ValidationException(emailErrorMsg);
            }
        }
    }

    public void validateId(String idInput) throws ValidationException {
        if (idInput != "" && idInput != null) {
            pattern = pattern.compile(id);
            matcher = pattern.matcher(idInput);

            if (matcher.find() || Integer.valueOf(idInput) <= 0) {
                throw new ValidationException(idErrorMsg);
            }
        }
    }

    public void validatePrice(String priceInput) throws ValidationException {
        if (priceInput != "" && priceInput != null) {
            pattern = pattern.compile(price);
            matcher = pattern.matcher(priceInput);

            if (!matcher.find()) {
                throw new ValidationException(priceErrorMsg);
            }
        }
    }

    public void validateStatus(String statusInput) throws ValidationException {
        if (statusInput != "" && statusInput != null) {
            pattern = pattern.compile(status);
            matcher = pattern.matcher(statusInput);

            if (!matcher.find()) {
                throw new ValidationException(statusErrorMsg);
            }
        } else {
            throw new ValidationException(statusErrorMsg);
        }
    }

    public static ValidationHelper getInstance() {
        return instance;
    }


}
