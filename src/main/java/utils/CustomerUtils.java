package utils;

import Jsons_present.MealJson;
import Jsons_present.SendOrderJson;
import com.redhat.model.Meal;
import com.redhat.model.Runner;

import java.text.SimpleDateFormat;
import java.util.*;

public class CustomerUtils {


    public static String getDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss a");
        Date date = new Date();
        return formatter.format(date);
    }

    public static int getTotalPrice(Set<Meal> meals ){
        int sum = 0;
        for (Meal meal : meals) {
            sum += meal.getPrice();
        }
        return sum;
    }

    public static Runner getRandomRunner(List<Runner> runners) {
        Random rand = new Random();
        int randomNum = rand.nextInt(((runners.size() - 1)) + 1);
        return runners.get(randomNum);
    }

    public static Set<MealJson> getMealsJson(Set<Meal> meals, List<SendOrderJson> sendOrderJsons) {

        Set<MealJson> mealJsons = new HashSet<>();
        for (SendOrderJson orderJson : sendOrderJsons) {
            for (Meal meal : meals) {
                if (meal.getId() == orderJson.getMealId()) {
                    mealJsons.add(new MealJson(meal.getId(), meal.getName(), meal.getPrice() * orderJson.getCount()));
                }
            }
        }

        return mealJsons;

    }

    public static Set<Meal> getMeals(Set<Meal> meals, List<SendOrderJson> sendOrderJsons) {

        Set<Meal> mealJsons = new HashSet<>();
        for (SendOrderJson orderJson : sendOrderJsons) {
            for (Meal meal : meals) {
                if (meal.getId() == orderJson.getMealId()) {
                    mealJsons.add(meal);
                }
            }
        }
        return mealJsons;
    }

    public static List<MealJson> convertOrderToJson(Set<Meal> meals) {
        List<MealJson> mealJsons = new ArrayList<>();
        for (Meal meal : meals) {
            mealJsons.add(new MealJson(meal.getId(), meal.getName(), meal.getPrice()));
        }
        return mealJsons;
    }
}
