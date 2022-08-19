package com.redhat.demo.webapp.consumer;

import java.math.BigDecimal;
import java.util.Date;

import javax.inject.Inject;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.annotations.SseElementType;

import io.smallrye.mutiny.Multi;
import io.vertx.core.json.JsonObject;
import io.vertx.mutiny.core.eventbus.EventBus;

@Path("/")
public class EarthOrderResource {

    @Inject
    EventBus eventBus;

    @GET
    @Path("/stream")
    @Produces(MediaType.SERVER_SENT_EVENTS)
    @SseElementType(MediaType.APPLICATION_JSON)
    public Multi<JsonObject> stream()
    {
        return eventBus.<JsonObject>consumer("order_stream")
                .toMulti()
                .map(b -> b.body());
    }

    /*
    @GET
    @Path("/try") 
    public String tryNow(){

        final long orderId = 110L;
        final String orderType = "E";
        final String orderItemName = "Lemon";
        final String quantity = "100";
        final String price = "50";
        final String shipmentAddress = "12345";
        final String zipCode = "12345";
        final String totalAmount = "5000";
        final String deliveryFee = "200";
        final String stateCode = "12345";
        final String stateName = "12345";

        //EarthOrder earthOrder = new EarthOrder(orderId, orderType, orderItemName, quantity, price, shipmentAddress, zipCode, totalAmount, deliveryFee, stateCode, stateName);

        //final JsonObject jsonObject = JsonObject.mapFrom(earthOrder);
        //eventBus.publish("order_stream", jsonObject);

        return "success";
    }
    */
    
}