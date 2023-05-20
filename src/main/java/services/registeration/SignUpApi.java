package services.registeration;

import com.redhat.model.User;

import javax.annotation.security.PermitAll;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Objects;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@PermitAll
@Stateless

@Path("/")
public class SignUpApi {

    @PersistenceContext
    private EntityManager manager;

    @POST
    @Path("signUp/")
    @Consumes(MediaType.APPLICATION_JSON)
    public String signUp(User user) {
        if (checkUserIfExist(user)) {
            return "User already exist";
        }
        manager.persist(user);
        return "Successfully sign up ,your id is " + user.getId();
    }

    @GET
    @Path("/getAllUsers")
    public List<User> test() {
        return getUsers();
    }

    public List<User> getUsers() {
        TypedQuery<User> q = manager.createQuery("SELECT user  FROM User user", User.class);
        return q.getResultList();
    }

    public boolean checkUserIfExist(User newUser) {
        List<User> users = getUsers();
        for (User user : users) {
            if (user.getUserName().equals(newUser.getUserName()) && Objects.equals(user.getPassword(), newUser.getPassword())) {
                return true;
            }
        }
        return false;
    }

}
