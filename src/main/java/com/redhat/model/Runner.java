package com.redhat.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
public class Runner implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String userName;
    private String password;

    private String status;

    private double delivery_fees;

    @OneToMany(mappedBy = "runner")
    private List<Orders> orders;

    public List<Orders> getOrders() {
        return orders;
    }

    public void setOrders(List<Orders> order) {
        this.orders = order;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getDelivery_fees() {
        return delivery_fees;
    }

    public void setDelivery_fees(double delivery_fees) {
        this.delivery_fees = delivery_fees;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
