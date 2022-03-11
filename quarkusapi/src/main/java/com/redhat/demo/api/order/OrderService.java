package com.redhat.demo.api.order;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class OrderService {

    @Inject
    EntityManager em;

    public List<Order> getOrders() {
        return this.em.createNativeQuery("select * from Orders", Order.class).getResultList();
    }

}
