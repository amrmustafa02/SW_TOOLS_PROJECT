package services.manager;

import com.redhat.model.Meal;
import com.redhat.model.Restaurant;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class MealManager {
    @PersistenceContext
    private EntityManager manager;

    public void addNewMeal(Meal meal) {
        manager.persist(meal);
    }

    public Meal getAllMeal(int id) {
        return manager.find(Meal.class, id);
    }

    public void updateMeal(Meal meal) {
        manager.merge(meal);
    }

}
