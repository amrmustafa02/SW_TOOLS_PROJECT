package services.manager;

import com.redhat.model.Orders;
import com.redhat.model.Restaurant;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class RestaurantManager {
    @PersistenceContext
    private EntityManager manager;

    public void addNewRestaurant(Restaurant restaurant) {
        manager.persist(restaurant);
    }

    public Restaurant getRestaurantDetails(int id) {
       return manager.find(Restaurant.class,id);
    }

    public void updateRestaurant(Restaurant restaurant) {
        manager.merge(restaurant);
    }
    public void addNewOrder(Orders orders) {
        manager.persist(orders);
    }


}
