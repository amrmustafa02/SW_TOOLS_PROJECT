package services.registeration;

import com.redhat.model.User;
import constants_data.RoleKeys;
import services.managers.UserManager;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Objects;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)

@Stateless

@Path("/")
public class SignUpApi {
    @Inject
    private UserManager manager;

    @POST
    @Path("signUp")
    @Consumes(MediaType.APPLICATION_JSON)
    public String signUp(User user) {
        // email
        if (!checkIfRoleIsAlready(user.getRole())) {
            return "please enter role correct,one of them(" + RoleKeys.RestaurantOwner + "," + RoleKeys.CustomerOwner + "," + RoleKeys.RunnerOwner + ")";
        }

        if (checkUserIfExist(user)) {
            return "User already exist";
        }

        System.out.println("test1");

        manager.addNewUser(user);

        return "Successfully sign up ,your id is " + user.getId();
    }


    @GET
    @Path("getAllUsers")
    public List<User> getUsers() {
        return manager.getAllUsers();
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

    public boolean checkIfRoleIsAlready(String role) {
        return RoleKeys.RunnerOwner.equals(role) || RoleKeys.RestaurantOwner.equals(role) || RoleKeys.CustomerOwner.equals(role);
    }
    

}
