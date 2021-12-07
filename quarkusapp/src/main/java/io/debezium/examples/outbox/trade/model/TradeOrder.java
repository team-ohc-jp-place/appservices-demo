/*
 * Copyright Debezium Authors.
 *
 * Licensed under the Apache Software License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package io.debezium.examples.outbox.trade.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class TradeOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orderId", updatable = false, nullable = false)
    //private long id;
    private long orderId;
    private String orderType;
    //追加
    private String orderItemName;
    private String quantity; //元からある int → String
    private String price; //元からある
    private String shipmentAddress;
    private String zipCode;
    private String totalAmount;
    private String deliveryFee;
    private String stateCode;
    private String stateName;

    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-dd-yyyy")
    //private Date openDate;
    //private String symbol;
    //private int quantity;
    //private String price;
    //private String orderFee;
    //private int accountId;

    TradeOrder() {
    }

    public TradeOrder(long orderId, String orderType, String orderItemName, String quantity, String price, String shipmentAddress, String zipCode, String totalAmount, String deliveryFee, String stateCode, String stateName) {
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

    //public long getId() {
    //    return id;
    //}

    //public void setId(long id) {
    //    this.id = id;
    //}

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

    //public Date getOpenDate() {
    //    return openDate;
    //}

    //public void setOpenDate(Date openDate) {
    //    this.openDate = openDate;
    //}

    //public String getSymbol() {
    //    return this.symbol;
    //}

    //public void setSymbol(String symbol){
    //    this.symbol = symbol;
    //}

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

    //public String getOrderFee(){
    //    return this.orderFee;
    //}

    //public void setOrderFee(String orderFee){
    //    this.orderFee = orderFee;
    //}

    //public int getAccountId(){
    //    return this.accountId;
    //}

    //public void setAccountId(int accountId){
    //    this.accountId = accountId;
    //}
}
