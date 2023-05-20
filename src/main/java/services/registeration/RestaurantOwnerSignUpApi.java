package services.registeration;

import com.redhat.model.User;
import constants_data.RoleKeys;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

@Path("/")
public class RestaurantOwnerSignUpApi {

    @POST
    @Path("RestaurantOwner/signUp/")
    @Consumes(MediaType.APPLICATION_JSON)
   public String signUp(User user) {
//        user.setRole(RoleKeys.RestaurantOwner);
        return "done";
    }

}
