package services.menues;

import com.redhat.model.Meal;
import com.redhat.model.Restaurant;
import services.managers.RestaurantManager;

import javax.ejb.EJB;
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
    RestaurantManager restaurantManager;

    @POST
    @Path("addNewRestaurant")
    public String addNewRestaurant(Restaurant restaurant) {
        restaurantManager.addNewUser(restaurant);
        return "your id is " + restaurant.getId();
    }

    @GET
    @Path("getRestaurantDetails")
    public List<Restaurant> getAllRestaurants() {
        return restaurantManager.getAllRestaurant();
    }

}
