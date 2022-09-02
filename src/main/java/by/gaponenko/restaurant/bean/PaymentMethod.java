package by.gaponenko.restaurant.bean;

import java.io.Serializable;
import java.util.Objects;

public class PaymentMethod implements Serializable {
    private Integer idPaymentMethod;
    private String name;

    public PaymentMethod(){}

    public PaymentMethod(Integer idPaymentMethod, String name) {
        this.idPaymentMethod = idPaymentMethod;
        this.name = name;
    }

    public Integer getIdPaymentMethod() {
        return idPaymentMethod;
    }

    public void setIdPaymentMethod(Integer idPaymentMethod) {
        this.idPaymentMethod = idPaymentMethod;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PaymentMethod)) return false;
        PaymentMethod that = (PaymentMethod) o;
        return getIdPaymentMethod().equals(that.getIdPaymentMethod()) && getName().equals(that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIdPaymentMethod(), getName());
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" +
                "idPaymentMethod=" + idPaymentMethod +
                ", name='" + name + '\'' +
                '}';
    }
}
