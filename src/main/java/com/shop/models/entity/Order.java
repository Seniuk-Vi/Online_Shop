//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.shop.models.entity;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Order implements Serializable {
    private int id;
    private int userId;
    private String status;
    private Date orderDate;

    public Order() {
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return this.userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getOrderDate() {
        return this.orderDate;
    }

    public void setOrderDate(String orderDate) {
        String sDate1 = orderDate;
        Date date1 = null;

        try {
            date1 = (new SimpleDateFormat("dd/MM/yyyy")).parse(sDate1);
        } catch (ParseException var5) {
            System.out.println("Exception: can't parse String to Date");
        }

        System.out.println(sDate1 + "\t" + date1);
        this.orderDate = date1;
    }

    public String toString() {
        return "Order{id=" + this.id + ", userId=" + this.userId + ", status='" + this.status + "', orderDate=" + this.orderDate + "}";
    }
}
