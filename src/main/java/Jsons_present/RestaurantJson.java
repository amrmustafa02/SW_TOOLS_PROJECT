package Jsons_present;


import java.util.List;

public class RestaurantJson {

    int id;
    String name;
    List<MealJson> mealJson;
    List<OrdersDetailsJson> orderJson;
    int ownerId;

    public List<OrdersDetailsJson> getOrderJson() {
        return orderJson;
    }

    public void setOrderJson(List<OrdersDetailsJson> orderJson) {
        this.orderJson = orderJson;
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

    public List<MealJson> getMealJson() {
        return mealJson;
    }

    public void setMealJson(List<MealJson> mealJson) {
        this.mealJson = mealJson;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }
}
