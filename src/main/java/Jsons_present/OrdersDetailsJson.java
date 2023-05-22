package Jsons_present;

import java.util.List;

public class OrdersDetailsJson {
    private String date;
    private String restaurantName;
    private List<MealJson> meals;

    private String runnerName;
    private double deliveryFees;
    private int totalReceipt;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public List<MealJson> getMeals() {
        return meals;
    }

    public void setMeals(List<MealJson> meals) {
        this.meals = meals;
    }

    public double getDeliveryFees() {
        return deliveryFees;
    }

    public void setDeliveryFees(double deliveryFees) {
        this.deliveryFees = deliveryFees;
    }

    public String getRunnerName() {
        return runnerName;
    }

    public void setRunnerName(String runnerName) {
        this.runnerName = runnerName;
    }

    public int getTotalReceipt() {
        return totalReceipt;
    }

    public void setTotalReceipt(int totalReceipt) {
        this.totalReceipt = totalReceipt;
    }
}
