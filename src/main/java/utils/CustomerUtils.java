package utils;

import Jsons_present.MealJson;
import Jsons_present.OrdersDetailsJson;
import Jsons_present.RestaurantJson;
import Jsons_present.SendOrderJson;
import com.redhat.model.Meal;
import com.redhat.model.Orders;
import com.redhat.model.Restaurant;
import com.redhat.model.Runner;
import services.manager.RestaurantManager;

import java.text.SimpleDateFormat;
import java.util.*;

public class CustomerUtils {


    public static String getDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss a");
        Date date = new Date();
        return formatter.format(date);
    }

    public static int getTotalPrice(Set<Meal> meals) {
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

    public static List<MealJson> convertMealsToJson(Set<Meal> meals) {
        List<MealJson> mealJsons = new ArrayList<>();
        for (Meal meal : meals) {
            mealJsons.add(new MealJson(meal.getId(), meal.getName(), meal.getPrice()));
        }
        return mealJsons;
    }

    public static OrdersDetailsJson convertOrderToJson(Orders orders1) {
        OrdersDetailsJson ordersDetailsJson = new OrdersDetailsJson();
        // set date
        ordersDetailsJson.setDate(orders1.getDate());
        // set meals
        ordersDetailsJson.setMeals(CustomerUtils.convertMealsToJson(orders1.getMeals()));
        // set fees
        ordersDetailsJson.setDeliveryFees(orders1.getRunner().getDelivery_fees());
        // set runner name
        ordersDetailsJson.setRunnerName(orders1.getRunner().getName());
        //set total price
        ordersDetailsJson.setTotalReceipt(orders1.getTotalPrice());
        //set restaurant
        ordersDetailsJson.setRestaurantName(orders1.getOrderRes().getName());
        //set status
        ordersDetailsJson.setStatus(orders1.getOrderStatus());

        return ordersDetailsJson;
    }

    public static RestaurantJson convertRestaurantToJason(Restaurant restaurant) {
        RestaurantJson restaurantJson = new RestaurantJson();
        restaurantJson.setName(restaurant.getName());
        restaurantJson.setId(restaurant.getId());
        restaurantJson.setMealJson(convertMealsToJson(restaurant.getMeals()));
        restaurantJson.setOrderJson(null);
        return restaurantJson;
    }

}
