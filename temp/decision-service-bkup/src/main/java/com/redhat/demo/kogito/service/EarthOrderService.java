package com.redhat.demo.kogito.service;

import com.redhat.demo.kogito.service.EarthOrder;

import java.math.BigDecimal;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.json.JsonObject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.kie.kogito.rules.RuleUnit;
import org.kie.kogito.rules.RuleUnitInstance;

@ApplicationScoped
public class EarthOrderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EarthOrderService.class);

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Inject
    RuleUnit<EarthOrderUnit> alertRuleUnit; //RuleUnit EarthOrderUnit.java 参照

    @Transactional(value = TxType.MANDATORY)
    public void decisionService(JsonNode event) throws JsonMappingException, JsonProcessingException {
        
        //LOGGER.info("event: {}", event.asText());
        LOGGER.info("event: {}", event);

        //event = objectMapper.readTree(event.asText());

        LOGGER.info("Order ID: " + event.get("orderId"));

        final long orderId = event.get("orderId").asLong();
        final String orderType = event.get("orderType").asText();
        final String orderItemName = event.get("orderItemName").asText();
        final BigDecimal quantity = new BigDecimal(event.get("quantity").toString());
        final BigDecimal price = new BigDecimal(event.get("price").toString());
        final String shipmentAddress = event.get("shipmentAddress").asText();
        final String zipCode = event.get("zipCode").asText();
        //final String totalAmount = event.get("totalAmount").asText();
        //final String deliveryFee = event.get("deliveryFee").asText();
        //final String stateCode = event.get("stateCode").asText();
        //final String stateName = event.get("stateName").asText();

        //ルールユニットの宣言
        //Setup Drools RuleUnit 
        EarthOrderUnit earthOrderUnit = new EarthOrderUnit();
        RuleUnitInstance<EarthOrderUnit> alertsvcInstance = alertRuleUnit.createInstance(earthOrderUnit);



        //EarthOrder earthOrder = new EarthOrder(orderId, orderType, orderItemName, quantity, price, shipmentAddress, zipCode, totalAmount, deliveryFee, stateCode, stateName);

        //final JsonObject jsonObject = JsonObject.mapFrom(earthOrder);

        //eventBus.publish("order_stream", jsonObject);

        //LOGGER.info("eventBusへ送信");
        
    }

    @Transactional(value=TxType.MANDATORY)
    public void orderLineUpdated(JsonNode event) {
        LOGGER.info("Processing 'OrderLineUpdated' event: {}", event);
    }
}
