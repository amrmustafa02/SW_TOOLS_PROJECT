package services.menues;


import Jsons_present.RunnerJson;
import com.redhat.model.Runner;
import services.manager.RunnerManager;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
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
    public List<RunnerJson> getAllRunners() {
        List<Runner> runners = runnerManager.getRunners();
        List<RunnerJson> result = new ArrayList<>();
        for (Runner runner : runners) {
            result.add(new RunnerJson(runner.getName(), runner.getStatus(), runner.getDelivery_fees(), null));
        }
        return result;
    }
}
