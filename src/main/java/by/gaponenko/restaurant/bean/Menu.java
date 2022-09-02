package by.gaponenko.restaurant.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Menu implements Serializable {

    private List<Dish> dishes = new ArrayList<>();

    public Menu(){}

    public Menu(List<Dish> dishes) {
        this.dishes = dishes;
    }

    public List<Dish> getDishes() {
        return dishes;
    }

    public void setDishes(List<Dish> dishes) {
        this.dishes = dishes;
    }

    public void add(Dish dish){
        this.dishes.add(dish);
    }

    public void remove(Dish dish){
        this.dishes.remove(dish);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Menu)) return false;
        Menu menu = (Menu) o;
        return Objects.equals(getDishes(), menu.getDishes());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDishes());
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" +
                "dishes=" + dishes +
                '}';
    }
}
