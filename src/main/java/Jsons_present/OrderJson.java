package Jsons_present;

public class OrderJson {


    double price;
    String orderStatus;

    public OrderJson(double price, String orderStatus) {
        this.price = price;
        this.orderStatus = orderStatus;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

}
