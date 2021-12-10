package com.redhat.demo.webapp.model;

import java.util.Date;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonFormat;

public class EarthOrder {

    private long orderId;
    private String orderType;
    private String orderItemName;
    private String quantity;
    private String price;
    private String shipmentAddress;
    private String zipCode;
    private String totalAmount;
    private String deliveryFee;
    private String stateCode;
    private String stateName;


    EarthOrder() {
    }

    public EarthOrder(long orderId, String orderType, String orderItemName, String quantity, String price, String shipmentAddress, String zipCode, String totalAmount, String deliveryFee, String stateCode, String stateName) {
        this.orderId = orderId;
        this.orderType = orderType;
        this.orderItemName = orderItemName;
        this.quantity = quantity;
        this.price = price;
        this.shipmentAddress = shipmentAddress;
        this.zipCode = zipCode;
        this.totalAmount = totalAmount;
        this.deliveryFee = deliveryFee;
        this.stateCode = stateCode;
        this.stateName = stateName;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public String getOrderType() {
        return this.orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getOrderItemName() {
        return this.orderItemName;
    }

    public void setOrderItemName(String orderItemName) {
        this.orderItemName = orderItemName;
    }

    public String getQuantity() {
        return this.quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        return this.price;
    }

    public void setPrice(String price){
        this.price = price;
    }

    public String getShipmentAddress() {
        return this.shipmentAddress;
    }

    public void setShipmentAddress(String shipmentAddress) {
        this.shipmentAddress = shipmentAddress;
    }

    public String getZipCode() {
        return this.zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getTotalAmount() {
        return this.totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getDeliveryFee() {
        return this.deliveryFee;
    }

    public void setDeliveryFee(String deliveryFee) {
        this.deliveryFee = deliveryFee;
    }

    public String getStateCode() {
        return this.stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public String getStateName() {
        return this.stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

}
