package by.gaponenko.restaurant.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

public class OrderForCooking implements Serializable {
    private static final long serialVersionUID = 5468356552228965368L;
    private Integer idOrder;
    private String dishName;
    private String paymentMethod;
    private String status;
    private int quantity;
    private BigDecimal price;

    public OrderForCooking(Integer idOrder, String dishName, String paymentMethod, int quantity, BigDecimal price, String status) {
        this.idOrder = idOrder;
        this.dishName = dishName;
        this.paymentMethod = paymentMethod;
        this.status = status;
        this.quantity = quantity;
        this.price = price;
    }

    public OrderForCooking() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderForCooking that)) return false;
        return getQuantity() == that.getQuantity() && getIdOrder().equals(that.getIdOrder()) && getDishName().equals(that.getDishName()) && getPaymentMethod().equals(that.getPaymentMethod()) && getPrice().equals(that.getPrice());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIdOrder(), getDishName(), getPaymentMethod(), getStatus(), getQuantity(), getPrice());
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" +
                "idOrder=" + idOrder +
                ", dishName='" + dishName + '\'' +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", status'" + status + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                '}';
    }
}
