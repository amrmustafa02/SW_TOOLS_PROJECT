package services.manager;


import com.redhat.model.Meal;
import com.redhat.model.Orders;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;


@Stateless
public class OrdersManager {

    @PersistenceContext
    private EntityManager manager;

    public void addNewOrder(Orders orders) {
        manager.persist(orders);
    }
    public void updateOrder(Orders meal) {
        manager.merge(meal);
    }

    public Orders getOrder(int id) {
        return manager.find(Orders.class, id);
    }

    public List<Orders> getAllOrders() {
        TypedQuery<Orders> q = manager.createQuery("SELECT order  FROM Orders order", Orders.class);
        return q.getResultList();
    }

}
