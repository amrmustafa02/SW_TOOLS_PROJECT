package services.menues;

import Jsons_present.MealJson;
import Jsons_present.RestaurantJson;
import com.redhat.model.Meal;
import com.redhat.model.Restaurant;
import services.manager.MealManager;
import services.manager.RestaurantManager;
import services.manager.UserManager;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Stateless
@Path("/RestaurantOwner")
public class RestaurantServicesApi {
    @Inject
    private RestaurantManager manager;
    @Inject
    private MealManager managerMeal;

    @POST
    @Path("addNewRestaurant")
    public String addNewRestaurant(Restaurant restaurant) {
        manager.addNewRestaurant(restaurant);

        // set restaurant and
        for (Meal meal : restaurant.getMeals()) {
            meal.setRestaurant(restaurant);
            managerMeal.addNewMeal(meal);
        }

        try {
            Restaurant restaurant1 = manager.getAllRestaurant(restaurant.getId());
            return "success create restaurant,your id:" + restaurant1.getId() + " ----" + restaurant1.getMeals().size();
        } catch (Exception exception) {
            return "Error";
        }

    }

    @GET
    @Path("getRestaurantDetails/{id}")
    public RestaurantJson getAllRestaurants(@PathParam("id") int id) {
        Restaurant restaurant = manager.getAllRestaurant(id);
        //convert from Meal to meal Json
        List<MealJson> mealJsons = new ArrayList<>();
        for (Meal meal : restaurant.getMeals()) {
            mealJsons.add(new MealJson(meal.getName(), meal.getPrice()));
        }
        RestaurantJson restaurantJson = new RestaurantJson();
        restaurantJson.setMealJson(mealJsons);
        restaurantJson.setId(restaurant.getId());
        restaurantJson.setName(restaurant.getName());
        return restaurantJson;
    }

    @POST
    @Path("addMeal/{id}")
    public String addNewMeal(Meal meal, @PathParam("id") int id) {
        Restaurant restaurant = manager.getAllRestaurant(id);
        meal.setRestaurant(restaurant);
        managerMeal.addNewMeal(meal);
        restaurant.getMeals().add(meal);
        manager.updateRestaurant(restaurant);
        return "add successfully";
    }


}
