package services.menues;

import Jsons_present.MealJson;
import Jsons_present.OrdersDetailsJson;
import Jsons_present.SendOrderJson;
import com.redhat.model.Meal;
import com.redhat.model.Orders;
import com.redhat.model.Restaurant;
import com.redhat.model.Runner;
import constants_data.OrderStatus;
import constants_data.RunnerStatus;
import services.manager.OrdersManager;
import services.manager.RestaurantManager;
import services.manager.RunnerManager;
import utils.CustomerUtils;

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
public class CustomerServiceApi {
    @Inject
    private RestaurantManager manager;
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

        return "add successfully+ your order id is " + order.getOrderId();
    }

    @GET
    @Path("getOrders")
    public List<OrdersDetailsJson> getAllOrders() {
        List<Orders> orders = ordersManager.getAllOrders();
        List<OrdersDetailsJson> ordersDetailsJsons = new ArrayList<>();
        for (Orders orders1 : orders) {
            OrdersDetailsJson ordersDetailsJson = new OrdersDetailsJson();
            // set date
            ordersDetailsJson.setDate(orders1.getDate());
            // set meals
            ordersDetailsJson.setMeals(CustomerUtils.convertOrderToJson(orders1.getMeals()));
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
            
            ordersDetailsJsons.add(ordersDetailsJson);

        }
        return ordersDetailsJsons;

    }


}
