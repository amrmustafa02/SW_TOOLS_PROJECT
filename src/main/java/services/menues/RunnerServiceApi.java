package services.menues;


import Jsons_present.OrderJson;
import Jsons_present.OrdersDetailsJson;
import Jsons_present.RunnerJson;
import com.redhat.model.Orders;
import com.redhat.model.Runner;

import constants_data.OrderStatus;
import constants_data.RoleKeys;
import constants_data.RunnerStatus;
import constants_data.UserData;
import services.manager.RunnerManager;
import utils.CustomerUtils;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Stateless
@Path("/Runner")
public class RunnerServiceApi {

    @Inject
    RunnerManager runnerManager;

    @GET
    @Path("getRunners")
    @RolesAllowed({"admin"})

    public List<RunnerJson> getAllRunners() {

        List<Runner> runners = runnerManager.getRunners();
        List<RunnerJson> result = new ArrayList<>();

        for (Runner runner : runners) {
            result.add(new RunnerJson(runner.getName(), runner.getStatus(), runner.getDelivery_fees(), null));
        }

        return result;
    }

    @POST
    @Path("markOrder/{runnerId}/{orderId}")
    @RolesAllowed({"Runner"})
    public String markOrder(@PathParam("orderId") int orderId, @PathParam("runnerId") int runnerId) {
        if (!UserData.userRole.equals(RoleKeys.RunnerOwner)) {
            return "please sign as runner";
        }

        //get runner
        Runner runner = runnerManager.getRunner(runnerId);

        // search the order in runner
        List<Orders> orders = runner.getOrders();

        // loop on orders and check the order
        for (Orders orders1 : orders) {
            if (orders1.getOrderId() == orderId) {
                orders1.setOrderStatus(OrderStatus.delivered);
                runner.setStatus(RunnerStatus.available);
                return "successfully delivered";
            }
        }

        return "order not found in this runner";
    }

    @GET
    @Path("getOrder/{id}")
    @RolesAllowed({"Runner"})
    public String getAllOrders(@PathParam("id") int id) {
        int totalNumberOfCompletedOrders = 0;
        Runner runner = runnerManager.getRunner(id);

        List<Orders> orders = runner.getOrders();

        for (Orders orders1 : orders) {
            if (orders1.getOrderStatus().equals(OrderStatus.delivered)) {
                totalNumberOfCompletedOrders++;
            }
        }
        return "total number of completed orders is " + totalNumberOfCompletedOrders;

    }
}
