package io.debezium.examples.outbox.trade.service;

import java.util.Date;
import java.math.BigDecimal;

import javax.inject.Inject;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.annotations.SseElementType;

import io.debezium.examples.outbox.trade.model.TradeOrder;
import io.smallrye.mutiny.Multi;
import io.vertx.core.json.JsonObject;
import io.vertx.mutiny.core.eventbus.EventBus;

@Path("/")
public class TradeOrderResource {

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

    @GET
    @Path("/try") 
    public String tryNow(){
        //final long orderId = 110L;
        //final String orderType = "BUY";
        //final Date openDate = new Date();
        //final String symbol = "E1";
        //final int quantity = 500;
        //final String price = new BigDecimal("19.99").toString();
        //final String orderFee= new BigDecimal("5.00").toString();
        //final int accountId = 12345;

        final long orderId = 110L;
        final String orderType = "E";
        final String orderItemName = "Lemon";
        final String quantity = new BigDecimal("100").toString();
        final String price = new BigDecimal("50").toString();
        final String shipmentAddress = "12345";
        final String zipCode = "12345";
        final String totalAmount = new BigDecimal("5000").toString();
        final String deliveryFee = new BigDecimal("200").toString();
        final String stateCode = "12345";
        final String stateName = "12345";

        //TradeOrder tradeOrder = new TradeOrder(orderId, orderType, openDate, symbol, quantity, price, orderFee, accountId);
        TradeOrder tradeOrder = new TradeOrder(orderId, orderType, orderItemName, quantity, price, shipmentAddress, zipCode, totalAmount, deliveryFee, stateCode, stateName);

        final JsonObject jsonObject = JsonObject.mapFrom(tradeOrder);
        eventBus.publish("order_stream", jsonObject);

        return "success";
    }
    
}