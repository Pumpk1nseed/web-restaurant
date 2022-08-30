package by.gaponenko.restaurant.bean;

import java.security.Timestamp;
import java.util.Objects;

public class Bill {
    private String idBill;
    private String idOrder;
    private String price;
    private String idPaymentMethod;
    private String status;
    private Timestamp dateTime;

    public Bill(){}

    public Bill(String idBill, String idOrder, String price, String idPaymentMethod, String status, Timestamp dateTime) {
        this.idBill = idBill;
        this.idOrder = idOrder;
        this.price = price;
        this.idPaymentMethod = idPaymentMethod;
        this.status = status;
        this.dateTime = dateTime;
    }

    public String getIdBill() {
        return idBill;
    }

    public void setIdBill(String idBill) {
        this.idBill = idBill;
    }

    public String getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(String idOrder) {
        this.idOrder = idOrder;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getIdPaymentMethod() {
        return idPaymentMethod;
    }

    public void setIdPaymentMethod(String idPaymentMethod) {
        this.idPaymentMethod = idPaymentMethod;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getDateTime() {
        return dateTime;
    }

    public void setDateTime(Timestamp dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Bill)) return false;
        Bill bill = (Bill) o;
        return getIdBill().equals(bill.getIdBill()) && getIdOrder().equals(bill.getIdOrder()) && getPrice().equals(bill.getPrice()) && getIdPaymentMethod().equals(bill.getIdPaymentMethod()) && Objects.equals(getStatus(), bill.getStatus()) && Objects.equals(getDateTime(), bill.getDateTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIdBill(), getIdOrder(), getPrice(), getIdPaymentMethod(), getStatus(), getDateTime());
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" +
                "idBill='" + idBill + '\'' +
                ", idOrder='" + idOrder + '\'' +
                ", price='" + price + '\'' +
                ", idPaymentMethod='" + idPaymentMethod + '\'' +
                ", status='" + status + '\'' +
                ", dateTime=" + dateTime +
                '}';
    }
}
