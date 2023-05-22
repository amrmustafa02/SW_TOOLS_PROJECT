package services.menues;

import Jsons_present.OrdersDetailsJson;
import Jsons_present.RestaurantJson;
import Jsons_present.SendOrderJson;
import com.redhat.model.*;
import constants_data.OrderStatus;
import constants_data.RoleKeys;
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
import java.util.Objects;
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
        if (!Objects.equals(UserData.userRole, RoleKeys.CustomerOwner)) {
            return "please sign in a customer";
        }


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
        Runner runner = CustomerUtils.getRandomRunner(runnerManager.getAvailableRunners());
        runner.setStatus(RunnerStatus.busy);

        // get total price
        int totalPrice = CustomerUtils.getTotalPrice(order.getMeals(), sendOrderJsons);

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

        return "add successfully your order id is " + order.getOrderId() + " and runner id is " + runner.getId();
    }

    @GET
    @Path("getOrders")
    public List<OrdersDetailsJson> getAllOrders() {
        if (!Objects.equals(UserData.userRole, RoleKeys.CustomerOwner)) {
            return null;
        }

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

    @POST
    @Path("addMeal/{orderId}")
    public String addMealToOrder(List<SendOrderJson> sendOrderJsons, @PathParam("orderId") int orderId) {
        if (!Objects.equals(UserData.userRole, RoleKeys.CustomerOwner)) {
            return "pleas sign in as customer";
        }

        List<Orders> orders = ordersManager.getAllOrders();

        for (Orders orders1 : orders) {
            if (orders1.getOrderId() == orderId) {

                if (orders1.getCustomerId() != UserData.id) {
                    return "order not fount in this user";
                }

                // check if order is cancel or delivery
                if (Objects.equals(orders1.getOrderStatus(), OrderStatus.canceled)) {
                    return "order is canceled";
                }
                if (Objects.equals(orders1.getOrderStatus(), OrderStatus.delivered)) {
                    return "order is delivered";
                }


                // check order in restaruant
                Set<Meal> mealsRes = orders1.getOrderRes().getMeals();

                // list exist orders
                List<SendOrderJson> orderJsons2 = new ArrayList<>();

                for (SendOrderJson orderJson : sendOrderJsons) {
                    int id = orderJson.getMealId();

                    for (Meal meal : mealsRes) {
                        if (meal.getId() == id) {
                            orderJsons2.add(orderJson);
                            break;
                        }
                    }

                }

                //get meals and set in order
                Set<Meal> meals2 = CustomerUtils.getMeals(orders1.getOrderRes().getMeals(), orderJsons2);
                for (Meal meal : meals2) {
                    orders1.getMeals().add(meal);
                    orders1.setTotalPrice((int) (orders1.getTotalPrice() + meal.getPrice()));
                }

                return "add successfully";
            }
        }
        return "order not found";

    }

    @POST
    @Path("deleteMeal/{orderId}/{mealId}")
    public String deleteMealToOrder(@PathParam("orderId") int orderId, @PathParam("mealId") int mealId) {
        if (!Objects.equals(UserData.userRole, RoleKeys.CustomerOwner)) {
            return "pleas sign in as customer";
        }
        List<Orders> orders = ordersManager.getAllOrders();

        for (Orders orders1 : orders) {
            if (orders1.getOrderId() == orderId) {
                if (Objects.equals(orders1.getOrderStatus(), OrderStatus.canceled)) {
                    return "order is canceled";
                }
                if (Objects.equals(orders1.getOrderStatus(), OrderStatus.delivered)) {
                    return "order is delivered";
                }
                boolean check = orders1.getMeals().removeIf(meal1 -> meal1.getId() == mealId);

                if (!check)
                    return "meal not found";


                int sum = 0;
                for (Meal meal : orders1.getMeals())
                    sum += meal.getPrice();


                orders1.setTotalPrice((int) (sum + orders1.getRunner().getDelivery_fees()));

                return "delete successfully";
            }
        }
        return "order not found,or meal not found in this order";

    }


}
