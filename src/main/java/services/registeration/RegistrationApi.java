package services.registeration;

import com.redhat.model.Runner;
import com.redhat.model.User;
import constants_data.RoleKeys;
import constants_data.RunnerStatus;
import services.manager.RunnerManager;
import services.manager.UserManager;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.lang.ref.PhantomReference;
import java.util.List;
import java.util.Objects;

@Consumes(MediaType.APPLICATION_JSON)

@Stateless

@Path("/")
public class RegistrationApi {
    @Inject
    private UserManager manager;
    @Inject
    private RunnerManager runnerManager;

    @POST
    @Path("signUp")
    @Consumes(MediaType.APPLICATION_JSON)
    public String signUp(User user) {
        // email
        if (!checkIfRoleIsAlready(user.getRole())) {
            return "please enter role correct,one of them(" + RoleKeys.RestaurantOwner + "," + RoleKeys.CustomerOwner + "," + RoleKeys.RunnerOwner + ")";
        }

        if (checkUserIfExist(user.getUserName(), user.getPassword())) {
            return "User already exist";
        }

        System.out.println("test1");
        if (RoleKeys.RunnerOwner.equals(user.getRole())) {
            Runner runner = new Runner();
            runner.setName(user.getName());
            runner.setStatus(RunnerStatus.available);
            runnerManager.addNewRunner(runner);
        } else
            manager.addNewUser(user);

        return "Successfully sign up ,your id is " + user.getId();
    }

    @GET
    @Path("login/{userName}/{password}")
    public String signIn(@PathParam("userName") String userName, @PathParam("password") String password) {
        if (!checkUserIfExist(userName, password)) {
            return "User not found,Please sign up first";
        }
        return "Success Login";
    }

    @GET
    @Path("getAllUsers")
    public List<User> getUsers() {
        return manager.getAllUsers();
    }

    public boolean checkUserIfExist(String userName, String password) {
        List<User> users = getUsers();
        for (User user : users) {
            if (user.getUserName().equals(userName) && Objects.equals(user.getPassword(), password)) {
                return true;
            }
        }
        return false;
    }

    public boolean checkIfRoleIsAlready(String role) {
        return RoleKeys.RunnerOwner.equals(role) || RoleKeys.RestaurantOwner.equals(role) || RoleKeys.CustomerOwner.equals(role);
    }


}
