package services.menues;

import Jsons_present.*;
import com.redhat.model.Meal;
import com.redhat.model.Orders;
import com.redhat.model.Restaurant;
import constants_data.OrderStatus;
import constants_data.RoleKeys;
import constants_data.UserData;
import services.manager.MealManager;
import services.manager.OrdersManager;
import services.manager.RestaurantManager;
import utils.CustomerUtils;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Stateless
@Path("/RestaurantOwner")
@RolesAllowed({"Res"})

public class RestaurantServiceApi {
    @Inject
    private RestaurantManager manager;
    @Inject
    private MealManager managerMeal;
    @Inject
    private OrdersManager orderManager;

    @POST
    @Path("addNewRestaurant")
    public String addNewRestaurant(Restaurant restaurant) {

        if (!UserData.userRole.equals(RoleKeys.RestaurantOwner))
            return "please sign in as restaurant owner";

        restaurant.setOwnerId(UserData.id);
        manager.addNewRestaurant(restaurant);

        // set restaurant
        for (Meal meal : restaurant.getMeals()) {
            meal.setRestaurant(restaurant);
            managerMeal.addNewMeal(meal);
        }

        for (Orders order : restaurant.getOrders()) {
            order.setOrderRes(restaurant);
            manager.addNewOrder(order);
        }

        try {
            return "success create restaurant,your id:" + restaurant.getId() ;
        } catch (Exception exception) {
            return "Error";
        }

    }

    @GET
    @Path("getRestaurantDetails/{id}")
    public RestaurantJson getRestaurantDetails(@PathParam("id") int id) {

        if (!UserData.userRole.equals(RoleKeys.RestaurantOwner))
            return null;

        Restaurant restaurant = manager.getRestaurantDetails(id);
        //convert from Meal to meal Json
        List<MealJson> mealJsons = new ArrayList<>();
        for (Meal meal : restaurant.getMeals()) {
            mealJsons.add(new MealJson(meal.getId(), meal.getName(), meal.getPrice()));
        }

        List<OrdersDetailsJson> orderJsons = new ArrayList<>();

        for (Orders order : restaurant.getOrders()) {
            orderJsons.add(CustomerUtils.convertOrderToJson(order));
        }

        RestaurantJson restaurantJson = new RestaurantJson();

        restaurantJson.setMealJson(mealJsons);
        restaurantJson.setId(restaurant.getId());
        restaurantJson.setName(restaurant.getName());
        restaurantJson.setOrderJson(orderJsons);
        restaurantJson.setOwnerId(restaurant.getOwnerId());

        return restaurantJson;
    }

    @POST
    @Path("addMeal/{id}")
    public String addNewMeal(Meal meal, @PathParam("id") int id) {

        if (!UserData.userRole.equals(RoleKeys.RestaurantOwner))
            return "please sign as restaurant owner";

        Restaurant restaurant = manager.getRestaurantDetails(id);
        meal.setRestaurant(restaurant);
        managerMeal.addNewMeal(meal);
        restaurant.getMeals().add(meal);
        manager.updateRestaurant(restaurant);
        return "add successfully+ " + meal.getId();
    }

    @POST
    @Path("UpdateMeal/{id}/{mealId}")

    public String updateMeal(Meal meal, @PathParam("id") int id, @PathParam("mealId") int mealId) {

        if (!UserData.userRole.equals(RoleKeys.RestaurantOwner))
            return "please sign as restaurant owner";

        Restaurant restaurant = manager.getRestaurantDetails(id);
        Set<Meal> meals = restaurant.getMeals();
        for (Meal resMeal : meals) {
            if (resMeal.getId() == mealId) {
                resMeal.setName(meal.getName());
                resMeal.setPrice(meal.getPrice());
                manager.updateRestaurant(restaurant);
                return "update successfully";
            }
        }
        return "meal not found in this restaurant";

    }

    @POST
    @Path("deleteMeal/{id}/{mealId}")
    public String deleteMeal(@PathParam("id") int id, @PathParam("mealId") int mealId) {

        if (!UserData.userRole.equals(RoleKeys.RestaurantOwner))
            return "please sign as restaurant owner";

        Restaurant restaurant = manager.getRestaurantDetails(id);
        Set<Meal> meals = restaurant.getMeals();
        for (Meal resMeal : meals) {
            if (resMeal.getId() == mealId) {
                managerMeal.removeMeal(resMeal);
                meals.remove(resMeal);
                manager.updateRestaurant(restaurant);
                return "deleted successfully+ " + meals.size();
            }
        }
        return "meal not found in this restaurant";
    }


    @GET
    @Path("getReport/{id}")
    public RestaurantReport getReport(@PathParam("id") int id) {

        if (!UserData.userRole.equals(RoleKeys.RestaurantOwner))
            return null;

        // var to sum
        int totalEarn = 0, completedOrders = 0, canceledOrders = 0;

        Restaurant restaurant = manager.getRestaurantDetails(id);
        Set<Orders> orders = restaurant.getOrders();
        for (Orders order : orders) {
            if (order.getOrderStatus().equals(OrderStatus.delivered)) {
                totalEarn += order.getTotalPrice();
                completedOrders++;
            } else if (order.getOrderStatus().equals(OrderStatus.canceled)) {
                canceledOrders++;
            }
        }
        return new RestaurantReport(totalEarn, completedOrders, canceledOrders);
    }


}
