package services.manager;

import com.redhat.model.User;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
public class UserManager {

    @PersistenceContext
    private EntityManager manager;

    public void addNewUser(User user) {
        manager.persist(user);
    }

    public List<User> getAllUsers() {
        TypedQuery<User> q = manager.createQuery("SELECT user  FROM User user", User.class);
        return q.getResultList();
    }
    public User getUser(int id) {
        return manager.find(User.class, id);
    }

}
