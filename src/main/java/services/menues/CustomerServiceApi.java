package services.menues;

import Jsons_present.OrdersDetailsJson;
import Jsons_present.RestaurantJson;
import Jsons_present.SendOrderJson;
import com.redhat.model.*;
import constants_data.OrderStatus;
import constants_data.RunnerStatus;
import constants_data.UserData;
import services.manager.OrdersManager;
import services.manager.RestaurantManager;
import services.manager.RunnerManager;
import services.manager.UserManager;
import utils.CustomerUtils;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Stateless
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("/customerService")
@RolesAllowed({"Customer"})

public class CustomerServiceApi {
    @Inject
    private RestaurantManager manager;
    @Inject
    private UserManager userManager;
    @Inject
    RunnerManager runnerManager;
    @Inject
    OrdersManager ordersManager;


    @POST
    @Path("addNewOrder/{idRestaurant}")
    public String addOrder(List<SendOrderJson> sendOrderJsons, @PathParam("idRestaurant") int id) {

        Restaurant restaurant = manager.getRestaurantDetails(id);

        if (restaurant == null) {
            return null;
        }


        Orders order = new Orders();

        // set customer id
        order.setCustomerId(UserData.id);
        // set restaurant name
        order.setOrderRes(restaurant);
        //set meals
        order.setMeals(CustomerUtils.getMeals(restaurant.getMeals(), sendOrderJsons));

        // get random runner
        Runner runner = CustomerUtils.getRandomRunner(runnerManager.getRunners());
        runner.setStatus(RunnerStatus.busy);

        // get total price
        int totalPrice = CustomerUtils.getTotalPrice(order.getMeals());

        // increase fees on total price
        totalPrice += runner.getDelivery_fees();

        //set total price
        order.setTotalPrice(totalPrice);
        // set date
        order.setDate(CustomerUtils.getDate());
        //set runner
        order.setRunner(runner);

        order.setOrderStatus(OrderStatus.preparing);

        ordersManager.addNewOrder(order);
        // add order to runner
        runner.getOrders().add(order);

        // add order to in restaurant
        restaurant.getOrders().add(order);

        return "add successfully+ your order id is " + order.getOrderId();
    }

    @GET
    @Path("getOrders/{id}")
    public List<OrdersDetailsJson> getAllOrders(@PathParam("id") int id) {

        List<Orders> orders = ordersManager.getAllOrders();
        List<OrdersDetailsJson> ordersDetailsJsons = new ArrayList<>();
        for (Orders orders1 : orders) {
            if (orders1.getCustomerId() == UserData.id)
                ordersDetailsJsons.add(CustomerUtils.convertOrderToJson(orders1));
        }
        return ordersDetailsJsons;

    }


    @GET
    @Path("getAllRestaurants")
    public List<RestaurantJson> getAllRestaurants() {
        List<Restaurant> restaurants = manager.getAllRestaurants();
        List<RestaurantJson> restaurantJsons = new ArrayList<>();
        // handle json
        for (Restaurant restaurant : restaurants) {
            restaurantJsons.add(CustomerUtils.convertRestaurantToJason(restaurant));
        }

        return restaurantJsons;
    }

    @Path("editOrder/{orderId}/{mealId}")
    public String addMealToOrder(Meal meal, @PathParam("orderId") int orderId, @PathParam("mealId") int mealId) {
        List<Orders> orders = ordersManager.getAllOrders();

        for (Orders orders1 : orders) {
            if (orders1.getOrderId() == orderId) {
                if (orders1.getCustomerId() != UserData.id) {
                    return "order not fount in this user";
                }
                orders1.getMeals().add(meal);
                return "add successfully";
            }
        }
        return "order not found";

    }


}
