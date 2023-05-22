package utils;

import Jsons_present.MealJson;
import Jsons_present.SendOrderJson;
import com.redhat.model.Meal;
import com.redhat.model.Runner;
import org.hibernate.criterion.Order;

import java.text.SimpleDateFormat;
import java.util.*;

public class CustomerUtils {


    public static String getDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss a");
        Date date = new Date();
        return formatter.format(date);
    }

    public static int getTotalPrice(Set<Meal> meals, List<SendOrderJson> sendOrderJsons) {
        int sum = 0;

        for (SendOrderJson orderJson : sendOrderJsons) {

            for (Meal meal : meals) {
                if (meal.getId() == orderJson.getMealId())
                    sum += (meal.getPrice() * orderJson.getCount());
            }
        }
        return sum;
    }

    public static Runner getRandomRunner(List<Runner> runners) {
        Random rand = new Random();
        int randomNum = rand.nextInt(((runners.size() - 1)) + 1);
        return runners.get(randomNum);
    }

    public static List<MealJson> getMeals(Set<Meal> meals, List<SendOrderJson> sendOrderJsons) {

        List<MealJson> mealJsons = new ArrayList<>();
        for (SendOrderJson orderJson : sendOrderJsons) {
            for (Meal meal : meals) {
                if (meal.getId() == orderJson.getMealId()) {
                    mealJsons.add(new MealJson(meal.getId(), meal.getName(), meal.getPrice() * orderJson.getCount()));
                }
            }

        }

        return mealJsons;

    }

}
