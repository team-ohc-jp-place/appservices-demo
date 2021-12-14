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

//@Entity
public class TradeOrder {

    //@Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    //@Column(name = "id", updatable = false, nullable = false)
    private long id;
    private long orderId;
    private String orderType;

    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-dd-yyyy")
    private Date openDate;
    private String symbol;
    private int quantity;
    private String price;
    private String orderFee;
    private int accountId;

    TradeOrder() {
    }

    public TradeOrder(long orderId, String orderType, Date openDate, String symbol, int quantity, String price, String orderFee, int accountId) {
        this.orderId = orderId;
        this.orderType = orderType;
        this.openDate = openDate;
        this.symbol = symbol;
        this.quantity = quantity;
        this.price = price;
        this.orderFee = orderFee;
        this.accountId = accountId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public Date getOpenDate() {
        return openDate;
    }

    public void setOpenDate(Date openDate) {
        this.openDate = openDate;
    }

    public String getSymbol() {
        return this.symbol;
    }

    public void setSymbol(String symbol){
        this.symbol = symbol;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        return this.price;
    }

    public void setPrice(String price){
        this.price = price;
    }

    public String getOrderFee(){
        return this.orderFee;
    }

    public void setOrderFee(String orderFee){
        this.orderFee = orderFee;
    }

    public int getAccountId(){
        return this.accountId;
    }

    public void setAccountId(int accountId){
        this.accountId = accountId;
    }
}
