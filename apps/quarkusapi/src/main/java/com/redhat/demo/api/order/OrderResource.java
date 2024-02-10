package com.redhat.demo.api.order;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Path("/orders")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)

public class OrderResource {
    @Inject
    OrderService service;
    
    @GET
    public List<Order> getOrders() {
        return this.service.getOrders();
    }
}