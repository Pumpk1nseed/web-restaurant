package by.gaponenko.restaurant.bean;

import java.math.BigDecimal;
import java.util.Objects;

public class Dish {
    private int idDish;
    private String name;
    private String description;
    private int idCategory;
    private String photoUrl;
    private BigDecimal price;
    private String status;

    public Dish(){}

    public Dish(int idDish, String name, String description, int idCategory, String photoUrl, BigDecimal price, String status) {
        this.idDish = idDish;
        this.name = name;
        this.description = description;
        this.idCategory = idCategory;
        this.photoUrl = photoUrl;
        this.price = price;
        this.status = status;
    }

    public int getIdDish() {
        return idDish;
    }

    public void setIdDish(int idDish) {
        this.idDish = idDish;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(int idCategory) {
        this.idCategory = idCategory;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Dish)) return false;
        Dish dish = (Dish) o;
        return getIdDish() == dish.getIdDish() && getIdCategory() == dish.getIdCategory() && getName().equals(dish.getName()) && Objects.equals(getDescription(), dish.getDescription()) && Objects.equals(getPhotoUrl(), dish.getPhotoUrl()) && getPrice().equals(dish.getPrice()) && Objects.equals(getStatus(), dish.getStatus());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIdDish(), getName(), getDescription(), getIdCategory(), getPhotoUrl(), getPrice(), getStatus());
    }

    @Override
    public String toString() {
        return  getClass().getSimpleName() + "{" +
                "idDish=" + idDish +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", idCategory=" + idCategory +
                ", photoUrl='" + photoUrl + '\'' +
                ", price=" + price +
                ", status='" + status + '\'' +
                '}';
    }
}
