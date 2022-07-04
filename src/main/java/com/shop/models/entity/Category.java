package com.shop.models.entity;

import java.io.Serializable;

public class Category implements Serializable {
    private String category;

    public Category() {
    }

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
