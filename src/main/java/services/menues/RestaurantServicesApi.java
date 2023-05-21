package services.menues;

import Jsons_present.MealJson;
import Jsons_present.OrderJson;
import Jsons_present.RestaurantJson;
import com.redhat.model.Meal;
import com.redhat.model.Orders;
import com.redhat.model.Restaurant;
import services.manager.MealManager;
import services.manager.OrdersManager;
import services.manager.RestaurantManager;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Stateless
@Path("/RestaurantOwner")
public class RestaurantServicesApi {
    @Inject
    private RestaurantManager manager;
    @Inject
    private MealManager managerMeal;
    @Inject
    private OrdersManager orderManager;

    @POST
    @Path("addNewRestaurant")
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
        Orders order = new Orders();
        order.setOrderRes(restaurant);
        order.setTotalPrice(12);
        order.setOrderStatus("busy");

        manager.addNewOrder(order);
        restaurant.getOrders().add(order);

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
            mealJsons.add(new MealJson(meal.getName(), meal.getPrice()));
        }
        List<OrderJson> orderJsons = new ArrayList<>();

        for (Orders order : restaurant.getOrders()) {

            orderJsons.add(new OrderJson(order.getTotalPrice(), order.getOrderStatus()));
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
        return "add successfully";
    }

    @POST
    @Path("addOrder")
    public OrderJson addOrder(Orders orders) {
//        order.setOrderRes(null);

        orderManager.addNewOrder(orders);
        return new OrderJson(orders.getTotalPrice(), orders.getOrderStatus());

    }

}
