package com.redhat.demo.webapp.service;

import java.util.Date;
import java.math.BigDecimal;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.redhat.demo.webapp.model.EarthOrder;
import io.vertx.core.json.JsonObject;
import io.vertx.mutiny.core.eventbus.EventBus;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@ApplicationScoped
public class EarthOrderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EarthOrderService.class);

    @Inject
    EventBus eventBus;

    @Transactional(value = TxType.MANDATORY)
    public void orderCreated(JsonNode event) throws JsonMappingException, JsonProcessingException {
        

        LOGGER.info("event: '{}'", event);

        JsonObject json = new JsonObject();
        json.put("orderId", event.get("orderId").asLong());
        json.put("orderType" , event.get("orderType").asText());
        json.put("orderItemName" , event.get("orderItemName").asText());
        json.put("quantity" , event.get("quantity").asText());
        json.put("price" , event.get("price").asText());
        json.put("shipmentAddress" , event.get("shipmentAddress").asText());
        json.put("zipCode" , event.get("zipCode").asText());
        json.put("totalAmount" , event.get("totalAmount").asText());
        json.put("deliveryFee" , event.get("deliveryFee").asText());
        json.put("stateCode" , event.get("stateCode").asText());
        json.put("stateName" , event.get("stateName").asText());

        LOGGER.info("chech json: '{}'" , json);

        /*
        final long orderId = event.get("orderId").asLong();
        final String orderType = event.get("orderType").asText();
        final String orderItemName = event.get("orderItemName").asText();
        final String quantity = event.get("quantity").asText();
        final String price = event.get("price").asText();
        final String shipmentAddress = event.get("shipmentAddress").asText();
        final String zipCode = event.get("zipCode").asText();
        final String totalAmount = event.get("totalAmount").asText();
        final String deliveryFee = event.get("deliveryFee").asText();
        final String stateCode = event.get("stateCode").asText();
        final String stateName = event.get("stateName").asText();
        */

        //LOGGER.info("Order ID: '{}'" , orderId);
        //LOGGER.info("Quantity: '{}'" , quantity);

        //EarthOrder earthOrder = new EarthOrder(orderId, orderType, orderItemName, quantity, price, shipmentAddress, zipCode, totalAmount, deliveryFee, stateCode, stateName);

        //LOGGER.info("set earthorder: '{}'" , earthOrder);

        //final JsonObject jsonObject = JsonObject.mapFrom(earthOrder);

        //LOGGER.info("set jsonObject: '{}'" , jsonObject);

        //eventBus.publish("order_stream", jsonObject);
        
        eventBus.publish("order_stream", json);
        //eventBus.publish("order_stream", event);

        LOGGER.info("eventBusへ送信");
        
    }

    @Transactional(value=TxType.MANDATORY)
    public void orderLineUpdated(JsonNode event) {
        LOGGER.info("Processing 'OrderLineUpdated' event: {}", event);
    }

}
