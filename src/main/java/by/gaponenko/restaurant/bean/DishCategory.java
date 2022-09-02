package by.gaponenko.restaurant.bean;

import java.io.Serializable;
import java.util.Objects;

public class DishCategory implements Serializable {

    private Integer idCategory;
    private String name;
    public DishCategory() {}

    public DishCategory(Integer idCategory, String name) {
        this.idCategory = idCategory;
        this.name = name;
    }

    public Integer getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(Integer idCategory) {
        this.idCategory = idCategory;
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
        if (!(o instanceof DishCategory)) return false;
        DishCategory that = (DishCategory) o;
        return getIdCategory().equals(that.getIdCategory()) && getName().equals(that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIdCategory(), getName());
    }

    @Override
    public String toString() {
        return  getClass().getSimpleName() + "{" +
                "idCategory=" + idCategory +
                ", name='" + name + '\'' +
                '}';
    }
}
