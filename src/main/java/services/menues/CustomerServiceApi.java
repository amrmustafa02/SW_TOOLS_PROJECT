package services.menues;

import Jsons_present.MealJson;
import Jsons_present.OrdersDetailsJson;
import Jsons_present.SendOrderJson;
import com.redhat.model.Meal;
import com.redhat.model.Orders;
import com.redhat.model.Restaurant;
import com.redhat.model.Runner;
import constants_data.RunnerStatus;
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

    @POST
    @Path("addNewOrder/{idRestaurant}")
    public OrdersDetailsJson addOrder(List<SendOrderJson> sendOrderJsons, @PathParam("idRestaurant") int id) {
        Restaurant restaurant = manager.getRestaurantDetails(id);
        if (restaurant == null) {
            return null;
        }

        OrdersDetailsJson ordersDetailsJson = new OrdersDetailsJson();

        //set date
        ordersDetailsJson.setDate(CustomerUtils.getDate());

        // set restaurant name
        ordersDetailsJson.setRestaurantName(restaurant.getName());

        // set meals
        ordersDetailsJson.setMeals(CustomerUtils.getMeals(restaurant.getMeals(), sendOrderJsons));

        // get random runner
        Runner runner = CustomerUtils.getRandomRunner(runnerManager.getRunners());

         //set Delivery Fees
        ordersDetailsJson.setDeliveryFees(runner.getDelivery_fees());

        //set runner name
        ordersDetailsJson.setRunnerName(runner.getName());

        // get total price
        int totalPrice = CustomerUtils.getTotalPrice(restaurant.getMeals(), sendOrderJsons);
        // increase fees on total price
        totalPrice += runner.getDelivery_fees();

        //set total amount
        ordersDetailsJson.setTotalReceipt(totalPrice);

        runner.setStatus(RunnerStatus.busy);



        // add order in database

        return ordersDetailsJson;
    }

    @GET
    @Path("getOrder")
    public SendOrderJson getOrder() {
        SendOrderJson orderJson = new SendOrderJson();
        return orderJson;
    }


}
