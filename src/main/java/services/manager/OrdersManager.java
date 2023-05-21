package services.manager;


import com.redhat.model.Orders;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Stateless
public class OrdersManager {

    @PersistenceContext
    private EntityManager manager;

    public void addNewOrder(Orders orders) {
        manager.persist(orders);
    }

}
