package com.redhat.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

public class Runner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private	int id;

    @NotNull
    private String name;
    @NotNull
    private String status;
    @NotNull
    private	double delivery_fees;

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


}
