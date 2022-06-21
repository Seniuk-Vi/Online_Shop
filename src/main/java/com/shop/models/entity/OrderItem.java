package com.shop.models.entity;

import java.io.Serializable;
import java.util.Objects;

public class OrderItem implements Serializable {

        private int orderId;

        private int productId;
        private int quantity;

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }


        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

    @Override
    public String toString() {
        return "OrderItem{" +
                "orderId=" + orderId +
                ", productId=" + productId +
                ", quantity=" + quantity +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderItem)) return false;
        OrderItem orderItem = (OrderItem) o;
        return getOrderId() == orderItem.getOrderId() && getProductId() == orderItem.getProductId() && getQuantity() == orderItem.getQuantity();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getOrderId(), getProductId(), getQuantity());
    }
}
