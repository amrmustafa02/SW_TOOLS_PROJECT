package com.redhat.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
public class Orders implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderId;
    private int totalPrice;
    private String orderStatus;
    @ManyToOne
    private Restaurant orderRes;
    @ManyToMany
    @JoinTable(
            name = "MealsXOrders",
            joinColumns = @JoinColumn(name = "orderId"),
            inverseJoinColumns = @JoinColumn(name = "mealId"))
    private Set<Meal> meals;
    @ManyToOne
    private Runner runner;

    public Set<Meal> getMeals() {
        return meals;
    }

    public void setMeals(Set<Meal> meals) {
        this.meals = meals;
    }


    public Runner getRunner() {
        return runner;
    }

    public void setRunner(Runner runner) {
        this.runner = runner;
    }

    public Orders() {

    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Restaurant getOrderRes() {
        return orderRes;
    }

    public void setOrderRes(Restaurant orderRes) {
        this.orderRes = orderRes;
    }
}
