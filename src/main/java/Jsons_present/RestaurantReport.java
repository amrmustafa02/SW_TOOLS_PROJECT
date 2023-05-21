package Jsons_present;

public class RestaurantReport {

    double totalEarn;
    int completedOrders;
    int canceledOrders;

    public RestaurantReport(double totalEarn, int completedOrders, int canceledOrders) {
        this.totalEarn = totalEarn;
        this.completedOrders = completedOrders;
        this.canceledOrders = canceledOrders;
    }

    public double getTotalEarn() {
        return totalEarn;
    }

    public void setTotalEarn(double totalEarn) {
        this.totalEarn = totalEarn;
    }

    public int getCompletedOrders() {
        return completedOrders;
    }

    public void setCompletedOrders(int completedOrders) {
        this.completedOrders = completedOrders;
    }

    public int getCanceledOrders() {
        return canceledOrders;
    }

    public void setCanceledOrders(int canceledOrders) {
        this.canceledOrders = canceledOrders;
    }
}
