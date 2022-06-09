package com.shop.models.entity;

public class OrderItem {

        private int orderId;

        private int productId;
        private int quantity;
        private double price;

        public int getProduct() {
            return productId;
        }

        public void setProduct(int productId) {
            this.productId = productId;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }


}
