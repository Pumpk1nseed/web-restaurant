package by.gaponenko.restaurant.bean;

import java.io.Serializable;
import java.util.Objects;

public class OrderForCooking implements Serializable {
    private static final long serialVersionUID = 5468356552228965368L;
    private Integer idOrder;
    private String dishName;
    private String paymentMethod;
    private int quantity;

    public OrderForCooking(Integer idOrder, String dishName, String paymentMethod, int quantity) {
        this.idOrder = idOrder;
        this.dishName = dishName;
        this.paymentMethod = paymentMethod;
        this.quantity = quantity;
    }

    public OrderForCooking() {
    }

    public Integer getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(Integer idOrder) {
        this.idOrder = idOrder;
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderForCooking that)) return false;
        return getQuantity() == that.getQuantity() && getIdOrder().equals(that.getIdOrder()) && getDishName().equals(that.getDishName()) && getPaymentMethod().equals(that.getPaymentMethod());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIdOrder(), getDishName(), getPaymentMethod(), getQuantity());
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" +
                "idOrder=" + idOrder +
                ", dishName='" + dishName + '\'' +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}
