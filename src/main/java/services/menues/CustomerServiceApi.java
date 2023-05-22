package services.menues;

import Jsons_present.MealJson;
import Jsons_present.SendOrderJson;
import com.redhat.model.Meal;
import com.redhat.model.Orders;
import com.redhat.model.Restaurant;
import services.manager.RestaurantManager;
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

    @POST
    @Path("addNewOrder/{idRestaurant}")
    public String addOrder(List<SendOrderJson> sendOrderJsons, @PathParam("idRestaurant") int id) {
        Restaurant restaurant = manager.getRestaurantDetails(id);
        if (restaurant == null) {
            return "restaurant is not found";
        }
        int totalPrice = CustomerUtils.getTotalPrice(restaurant.getMeals(), sendOrderJsons);


        return "done total price is = " + totalPrice;
    }

    @GET
    @Path("getOrder")
    public SendOrderJson addOrder() {
        SendOrderJson orderJson = new SendOrderJson();
        return orderJson;
    }


}
