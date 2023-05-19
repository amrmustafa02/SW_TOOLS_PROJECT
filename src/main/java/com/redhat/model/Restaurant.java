package com.redhat.model;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private ArrayList<Meal> meal;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @OneToMany()
    public ArrayList<Meal> getMeal() {
        return meal;
    }

    public void setMeal(ArrayList<Meal> meal) {
        this.meal = meal;
    }
}
