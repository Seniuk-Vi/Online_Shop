//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.shop.models.entity;

import java.io.Serializable;
import java.util.Objects;

public class OrderItem implements Serializable {
    private int orderId;
    private int productId;
    private int quantity;

    public OrderItem() {
    }

    public int getOrderId() {
        return this.orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getProductId() {
        return this.productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String toString() {
        return "OrderItem{orderId=" + this.orderId + ", productId=" + this.productId + ", quantity=" + this.quantity + "}";
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (!(o instanceof OrderItem)) {
            return false;
        } else {
            OrderItem orderItem = (OrderItem)o;
            return this.getOrderId() == orderItem.getOrderId() && this.getProductId() == orderItem.getProductId() && this.getQuantity() == orderItem.getQuantity();
        }
    }

    public int hashCode() {
        return Objects.hash(new Object[]{this.getOrderId(), this.getProductId(), this.getQuantity()});
    }
}
