package Jsons_present;


import java.util.List;

public class RestaurantJson {

    int id;
    String name;
    List<MealJson> mealJson;

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
}