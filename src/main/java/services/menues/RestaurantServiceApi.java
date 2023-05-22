package services.menues;

import Jsons_present.*;
import com.redhat.model.Meal;
import com.redhat.model.Orders;
import com.redhat.model.Restaurant;
import constants_data.OrderStatus;
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
public class RestaurantServiceApi {
    @Inject
    private RestaurantManager manager;
    @Inject
    private MealManager managerMeal;
    @Inject
    private OrdersManager orderManager;

    @POST
    @Path("addNewRestaurant")
    @RolesAllowed({"Res"})
    public String addNewRestaurant(Restaurant restaurant) {

        System.out.println("------------------------------------------------------");

        manager.addNewRestaurant(restaurant);
        // set restaurant and
        for (Meal meal : restaurant.getMeals()) {
            meal.setRestaurant(restaurant);
            managerMeal.addNewMeal(meal);
        }
        System.out.println("------------------------------------------------------");

        for (Orders order : restaurant.getOrders()) {
            order.setOrderRes(restaurant);
            manager.addNewOrder(order);
        }
//        Orders order = new Orders();
//        order.setOrderRes(restaurant);
//        order.setTotalPrice(12);
//        order.setOrderStatus("busy");

//        manager.addNewOrder(order);
//        restaurant.getOrders().add(order);

        try {
            Restaurant restaurant1 = manager.getRestaurantDetails(restaurant.getId());
            return "success create restaurant,your id:" + restaurant1.getId() + " ----" + restaurant1.getOrders().size();
        } catch (Exception exception) {
            return "Error";
        }

    }

    @GET
    @Path("getRestaurantDetails/{id}")
    public RestaurantJson getRestaurantDetails(@PathParam("id") int id) {
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

        return restaurantJson;
    }

    @POST
    @Path("addMeal/{id}")
    public String addNewMeal(Meal meal, @PathParam("id") int id) {
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

    @POST
    @Path("addOrder")
    @RolesAllowed({"Customer"})
    public OrderJson addOrder(Orders orders) {
        // order.setOrderRes(null);
        orderManager.addNewOrder(orders);
        return new OrderJson(orders.getTotalPrice(), orders.getOrderStatus());
    }

    @GET
    @Path("getReport/{id}")
    public RestaurantReport getReport(@PathParam("id") int id) {
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
