package by.gaponenko.restaurant.service.validation;

import by.gaponenko.restaurant.bean.Order;
import by.gaponenko.restaurant.bean.criteria.Criteria;

import java.util.Map;

public class OrderValidator {

    private static final OrderValidator instance = new OrderValidator();

    private static final ValidationHelper helper = ValidationHelper.getInstance();

    private OrderValidator() {
    }

    public void validate(Order order, int idUser) throws ValidationException {
        if (order == null) {
            throw new ValidationException("Order is null while trying to create order.");
        }
        if (Integer.toString(idUser) == null) {
            throw new ValidationException("Id User is null while trying to create order.");
        }
        helper.validateId(Integer.toString(idUser));
    }

    public void validate(int idUser) throws ValidationException {
        helper.validateId(Integer.toString(idUser));
    }

    public void validate(int idOrder, int idDish, Integer quantity, int idPaymentMethod) throws ValidationException {
        helper.validateId(Integer.toString(idOrder));
        helper.validateId(Integer.toString(idDish));
        helper.validateId(Integer.toString(idPaymentMethod));
        if (quantity < 0) {
            throw new ValidationException("Quantity of dish should be above zero when trying to create order details.");
        }
    }

    public void validate(Criteria criteria) throws ValidationException {
        if (criteria == null) {
            throw new ValidationException("Criteria is null while trying to find order with.");
        }
        for (Map.Entry<String, Object> entry : criteria.getCriteria().entrySet()) {
            if (entry.getValue() == null) {
                throw new ValidationException(String.format("%s is null!", entry.getKey()));
            }
        }
    }

    public static OrderValidator getInstance() {
        return instance;
    }

}
