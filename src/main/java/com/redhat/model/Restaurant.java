package com.redhat.model;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Entity
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;
    @OneToMany(mappedBy = "restaurant")
    private Set<Meal> meals;
    private int ownerId;
//    @OneToMany(mappedBy = "restaurant")
//    private Set<Order> orders;

    public Restaurant() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public Set<Order> getOrders() {
//        return orders;
//    }
//
//    public void setOrders(Set<Order> orders) {
//        this.orders = orders;
//    }

    public int getId() {
        return id;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public void setId(int id) {
        this.id = id;
    }


    public Set<Meal> getMeals() {
        return meals;
    }

    public void setMeals(Set<Meal> meals) {
        this.meals = meals;
    }
}
