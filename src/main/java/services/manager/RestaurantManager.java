package services.manager;

import com.redhat.model.Meal;
import com.redhat.model.Restaurant;
import com.redhat.model.User;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
public class RestaurantManager {
    @PersistenceContext
    private EntityManager manager;

    public void addNewRestaurant(Restaurant restaurant) {
        manager.persist(restaurant);
    }

    public Restaurant getAllRestaurant(int id) {
       return manager.find(Restaurant.class,id);
    }

    public void updateRestaurant(Restaurant restaurant) {
        manager.merge(restaurant);
    }


}
