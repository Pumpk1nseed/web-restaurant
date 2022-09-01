package by.gaponenko.restaurant.bean;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Order {
    private Integer idOrder;
    private Map<Dish, Integer> orderList = new HashMap<>(); // dish-amount
    private Integer idUser;
    private Timestamp dateTime;
    private String status;
    private BigDecimal price;

    public Order(){}

    public Order(Integer idOrder, Map<Dish, Integer> orderList, Integer idUser, Timestamp dateTime, String status, BigDecimal price) {
        this.idOrder = idOrder;
        this.orderList = orderList;
        this.idUser = idUser;
        this.dateTime = dateTime;
        this.status = status;
        this.price = price;
    }

    public Integer getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(Integer idOrder) {
        this.idOrder = idOrder;
    }

    public Map<Dish, Integer> getOrderList() {
        return orderList;
    }

    public void setOrderList(Map<Dish, Integer> orderList) {
        this.orderList = orderList;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public Timestamp getDateTime() {
        return dateTime;
    }

    public void setDateTime(Timestamp dateTime) {
        this.dateTime = dateTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
        if (!(o instanceof Order)) return false;
        Order order = (Order) o;
        return getIdOrder().equals(order.getIdOrder()) && getOrderList().equals(order.getOrderList()) && getIdUser().equals(order.getIdUser()) && getDateTime().equals(order.getDateTime()) && Objects.equals(getStatus(), order.getStatus()) && getPrice().equals(order.getPrice());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIdOrder(), getOrderList(), getIdUser(), getDateTime(), getStatus(), getPrice());
    }

    @Override
    public String toString() {
        return  getClass().getSimpleName() + "{" +
                "idOrder=" + idOrder +
                ", orderList=" + orderList +
                ", idUser=" + idUser +
                ", dateTime=" + dateTime +
                ", status='" + status + '\'' +
                ", price=" + price +
                '}';
    }

}
