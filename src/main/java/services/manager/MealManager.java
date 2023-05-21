package services.manager;

import com.redhat.model.Meal;
import com.redhat.model.User;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
public class MealManager {
    @PersistenceContext
    private EntityManager manager;

    public void addNewMeal(Meal meal) {
        manager.persist(meal);
    }

    public void updateMeal(Meal meal) {
        manager.merge(meal);
    }

    public Meal getMeal(int id) {
        return manager.find(Meal.class, id);
    }

    public List<Meal> getAllMeals() {
        TypedQuery<Meal> q = manager.createQuery("SELECT meal  FROM Meal meal", Meal.class);
        return q.getResultList();
    }

    public void removeMeal(Meal meal) {
        manager.remove(meal);
    }

}
