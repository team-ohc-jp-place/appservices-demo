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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.redhat.demo.webapp.service.EarthOrderService;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@ApplicationScoped
public class OrderEventHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderEventHandler.class);

    @Inject
    EarthOrderService shipmentService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Transactional
    public void onOrderEvent(String payload) {

        try {

            LOGGER.info("イベント処理を開始します");

            JsonNode eventJson = deserialize(payload);

            LOGGER.info("デシリアライズ完了");

            //JsonNode eventPayload = eventJson.get("PAYLOAD");
            JsonNode eventPayload = eventJson;

            //LOGGER.info("受信メッセージ: '{}'", eventPayload.asText());
            LOGGER.info("受信メッセージ: '{}'", eventPayload);


            shipmentService.orderCreated(eventPayload);

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
