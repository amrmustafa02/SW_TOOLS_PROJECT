package services.menues;

import com.redhat.model.Meal;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import java.util.ArrayList;

@Path("/RestaurantOwner")
public class RestaurantServicesApi {


    @POST
    @Path("CreateMenu")
    public String createMenu(ArrayList<Meal> meals) {
        for (Meal meal : meals) {
            System.out.println(meal.getName());
        }
        return "good";
    }
}
