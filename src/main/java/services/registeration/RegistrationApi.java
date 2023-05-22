package services.registeration;

import com.redhat.model.Runner;
import com.redhat.model.User;
import constants_data.RoleKeys;

import constants_data.UserData;
import services.manager.RunnerManager;
import services.manager.UserManager;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import java.util.List;
import java.util.Objects;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)

@PermitAll
@Stateless

@Path("/")
public class RegistrationApi {
    @Inject
    private UserManager manager;
    @Inject
    private RunnerManager runnerManager;

    @POST
    @Path("userSignUp")
    @RolesAllowed({"Customer", "Res"})
    public String userSignUp(User user) {

        if (!checkIfRoleIsAlready(user.getRole())) {
            return "please enter role correct,one of them(" + RoleKeys.RestaurantOwner + "," + RoleKeys.CustomerOwner + "," + RoleKeys.RunnerOwner + ")";
        }

        if (checkUserIfExist(user.getUserName(), user.getPassword())) {
            return "User already exist";
        }

        manager.addNewUser(user);

        UserData.userRole = user.getRole();
        UserData.id = user.getId();

        return "Successfully sign up as user ,your id is " + user.getId();
    }

    @POST
    @Path("runnerSignUp")
    @RolesAllowed({"Runner"})
    public String runnerSignUp(Runner runner) {
        if (checkRunnerIfExist(runner.getUserName(), runner.getPassword())) {
            return "Runner already exist";
        }

        runnerManager.addNewRunner(runner);

        UserData.userRole = RoleKeys.RunnerOwner;
        UserData.id = runner.getId();


        return "Successfully sign up as runner ,your id is " + runner.getId();
    }

    @GET
    @Path("login/{userName}/{password}")
    @RolesAllowed({"Customer", "Res"})

    public String signIn(@PathParam("userName") String userName, @PathParam("password") String password) {
        if (!checkUserIfExist(userName, password)) {
            return "User not found,Please sign up first";
        }
        List<User> users = manager.getAllUsers();
        for (User user1 : users) {
            if (user1.getUserName().equals(userName) && user1.getPassword().equals(password)) {
                UserData.userRole = user1.getRole();
                UserData.id = user1.getId();

                break;
            }
        }
        return "Success Login";
    }

    @GET
    @Path("runnerLogin/{userName}/{password}")
    @RolesAllowed({"Runner"})
    public String runnerSignIn(@PathParam("userName") String userName, @PathParam("password") String password) {
        if (!checkRunnerIfExist(userName, password)) {
            return "Runner not found,Please sign up first";
        }
        for (Runner user1 : runnerManager.getAllRunners()) {
            if (user1.getUserName().equals(userName) && user1.getPassword().equals(password)) {
                UserData.userRole = RoleKeys.RunnerOwner;
                UserData.id = user1.getId();
                break;
            }
        }

        return "Success Login";
    }

    @GET
    @Path("getAllUsers")
    @RolesAllowed({"admin"})
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

    public boolean checkRunnerIfExist(String userName, String password) {
        List<Runner> users = runnerManager.getAllRunners();
        for (Runner user : users) {
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
