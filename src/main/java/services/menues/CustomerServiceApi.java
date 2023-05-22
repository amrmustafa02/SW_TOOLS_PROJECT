package services.menues;

import Jsons_present.OrdersDetailsJson;
import Jsons_present.SendOrderJson;
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

        // add order to runner


        // add order to in restaurant
         restaurant.getOrders().add(order);

        return "add successfully+ your order id is " + order.getOrderId();
    }

    @GET
    @Path("getOrders")
    public List<OrdersDetailsJson> getAllOrders() {
        List<Orders> orders = ordersManager.getAllOrders();
        List<OrdersDetailsJson> ordersDetailsJsons = new ArrayList<>();
        for (Orders orders1 : orders) {
            ordersDetailsJsons.add(CustomerUtils.convertOrderToJson(orders1));
        }
        return ordersDetailsJsons;

    }


}
