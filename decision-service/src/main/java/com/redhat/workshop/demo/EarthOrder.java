package com.redhat.workshop.demo;

import java.math.BigDecimal;

import javax.validation.OverridesAttribute;

public class EarthOrder {

    long orderId;
    String orderType;
    String orderItemName;
    BigDecimal quantity;
    BigDecimal price;
    String shipmentAddress;
    String zipCode;
    BigDecimal totalAmount;
    BigDecimal deliveryFee;
    String stateCode;
    String stateName;

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public String getOrderType() {
        return this.orderType;
    }

    public void setOrderType(final String orderType) {
        this.orderType = orderType;
    }

    public String getOrderItemName() {
        return this.orderItemName;
    }

    public void setOrderItemName(final String orderItemName) {
        this.orderItemName = orderItemName;
    }

    public BigDecimal getQuantity() {
        return this.quantity;
    }

    public void setQuantity(final BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return this.price;
    }

    public void setPrice(final BigDecimal price) {
        this.price = price;
    }

    public String getShipmentAddress() {
        return this.shipmentAddress;
    }

    public void setShipmentAddress(final String shipmentAddress) {
        this.shipmentAddress = shipmentAddress;
    }

    public String getZipCode() {
        return this.zipCode;
    }

    public void setZipCode(final String zipCode) {
        this.zipCode = zipCode;
    }

    public BigDecimal getTotalAmount() {
        return this.totalAmount;
    }

    public void setTotalAmount(final BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getDeliveryFee() {
        return this.deliveryFee;
    }

    public void setDeliveryFee(final BigDecimal deliveryFee) {
        this.deliveryFee = deliveryFee;
    }

    public String getStateCode() {
        return this.stateCode;
    }

    public void setStateCode(final String stateCode) {
        this.stateCode = stateCode;
    }

    public String getStateName() {
        return this.stateName;
    }

    public void setStateName(final String stateName) {
        this.stateName = stateName;
    }

    @Override
    public String toString(){

        return "OrderItemName:["+orderItemName+"]";
    }


}
