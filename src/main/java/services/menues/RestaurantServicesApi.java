package services.menues;

import com.redhat.model.Meal;
import com.redhat.model.Restaurant;
import services.manager.RestaurantManager;
import services.manager.UserManager;

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

    @POST
    @Path("addNewRestaurant")
    public String addNewRestaurant(Restaurant restaurant) {
        try {
            manager.addNewRestaurant(restaurant);
            return "success create restaurant,your id : " + restaurant.getId();
        } catch (Exception exception) {
            return "Error";
        }

    }

    @GET
    @Path("getRestaurantDetails")
    public List<Restaurant> getAllRestaurants() {
        return manager.getAllRestaurant();
    }
}
