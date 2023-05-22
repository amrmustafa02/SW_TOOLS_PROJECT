package services.manager;

import com.redhat.model.Meal;
import com.redhat.model.Runner;
import constants_data.RunnerStatus;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;


@Stateless
public class RunnerManager {

    @PersistenceContext
    private EntityManager manager;

    public void addNewRunner(Runner runner) {
        manager.persist(runner);
    }

    public void updateRunner(Runner runner) {
        manager.merge(runner);

    }

    public Runner getRunner(int id) {
        return manager.find(Runner.class, id);
    }

    public List<Runner> getRunners() {

        TypedQuery<Runner> q = manager.createQuery("SELECT runner FROM Runner runner", Runner.class);
        List<Runner> runners = q.getResultList();

        List<Runner> result = new ArrayList<>();

        for (Runner runner : runners) {
            if (runner.getStatus().equals(RunnerStatus.available)) {
                result.add(runner);
            }
        }
        return result;
    }


}
