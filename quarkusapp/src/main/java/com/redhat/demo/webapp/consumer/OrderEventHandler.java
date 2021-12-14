/*
 * Copyright Debezium Authors.
 *
 * Licensed under the Apache Software License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package com.redhat.demo.webapp.consumer;

import java.io.IOException;
import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import io.vertx.core.json.JsonObject;
import io.vertx.mutiny.core.eventbus.EventBus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@ApplicationScoped
public class OrderEventHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderEventHandler.class);

    @Inject
    EventBus eventBus;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Transactional
    public void onOrderEvent(String payload) {

        try {

            LOGGER.info("イベント処理を開始します");

            JsonNode eventPayload = deserialize(payload);

            LOGGER.info("受信メッセージ: '{}'", eventPayload);

            JsonObject json = new JsonObject();
            json.put("orderId", eventPayload.get("orderId").asLong());
            json.put("orderType" , eventPayload.get("orderType").asText());
            json.put("orderItemName" , eventPayload.get("orderItemName").asText());
            json.put("quantity" , eventPayload.get("quantity").asText());
            json.put("price" , eventPayload.get("price").asText());
            json.put("shipmentAddress" , eventPayload.get("shipmentAddress").asText());
            json.put("zipCode" , eventPayload.get("zipCode").asText());
            json.put("totalAmount" , eventPayload.get("totalAmount").asText());
            json.put("deliveryFee" , eventPayload.get("deliveryFee").asText());
            json.put("stateCode" , eventPayload.get("stateCode").asText());
            json.put("stateName" , eventPayload.get("stateName").asText());

            LOGGER.info("JsonObjectへ変換: '{}'", json);

            eventBus.publish("order_stream", json);
    
            LOGGER.info("eventBusへ送信");

        }
        catch(Exception e) {
            LOGGER.error("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private JsonNode deserialize(String event) {
        JsonNode eventPayload;

        try {
            //String unescaped = objectMapper.readValue(event, String.class);
            //event = event.replaceAll("\\\\\"", "\"");
            eventPayload = objectMapper.readTree(event);
        }
        catch (IOException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Couldn't deserialize event", e);
        }

        return eventPayload;
        //return eventPayload.get("payload").get("after");
        //return eventPayload.has("schema") ? eventPayload.get("payload").get("after") : eventPayload;
    }

    public static void main(String[] args){

    }
}
