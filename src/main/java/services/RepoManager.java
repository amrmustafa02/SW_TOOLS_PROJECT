package services;

import com.redhat.model.User;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
public class RepoManager {

    @PersistenceContext
    private EntityManager manager;

    public void addNewUser(User user) {
        manager.persist(user);
    }

    public List<User> getAllUsers() {
        TypedQuery<User> q = manager.createQuery("SELECT user  FROM User user", User.class);
        return q.getResultList();
    }
}
