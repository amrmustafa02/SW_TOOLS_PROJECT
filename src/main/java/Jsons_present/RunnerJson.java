package Jsons_present;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.List;

public class RunnerJson {

    private String name;

    private String status;

    private double delivery_fees;

    List<OrderJson> orderJsons;

    public RunnerJson(String name, String status, double delivery_fees, List<OrderJson> orderJsons) {
        this.name = name;
        this.status = status;
        this.delivery_fees = delivery_fees;
        this.orderJsons = orderJsons;
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

    public List<OrderJson> getOrderJsons() {
        return orderJsons;
    }

    public void setOrderJsons(List<OrderJson> orderJsons) {
        this.orderJsons = orderJsons;
    }
}
