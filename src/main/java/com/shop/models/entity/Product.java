package com.shop.models.entity;

import java.io.Serializable;
import java.util.Objects;

public class Product implements Serializable {
    private int id;
    private String title;
    private String description;
    private double price;
    private String imageUrl;
    private int modelYear;
    private int inStock;
    private String category;
    private String condition;

    public Product() {
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return this.price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getModelYear() {
        return this.modelYear;
    }

    public void setModelYear(int modelYear) {
        this.modelYear = modelYear;
    }

    public int getInStock() {
        return this.inStock;
    }

    public void setInStock(int inStock) {
        this.inStock = inStock;
    }

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCondition() {
        return this.condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String toString() {
        return "Product{id=" + this.id + ", title='" + this.title + "', description='" + this.description + "', price=" + this.price + ", imageUrl='" + this.imageUrl + "', modelYear=" + this.modelYear + ", inStock=" + this.inStock + ", category='" + this.category + "', condition='" + this.condition + "'}";
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (!(o instanceof Product)) {
            return false;
        } else {
            Product product = (Product)o;
            return this.getId() == product.getId() && Double.compare(product.getPrice(), this.getPrice()) == 0 && this.getModelYear() == product.getModelYear() && this.getInStock() == product.getInStock() && this.getTitle().equals(product.getTitle()) && this.getDescription().equals(product.getDescription()) && this.getImageUrl().equals(product.getImageUrl()) && this.getCategory().equals(product.getCategory()) && this.getCondition().equals(product.getCondition());
        }
    }

    public int hashCode() {
        return Objects.hash(new Object[]{this.getId(), this.getTitle(), this.getDescription(), this.getPrice(), this.getImageUrl(), this.getModelYear(), this.getInStock(), this.getCategory(), this.getCondition()});
    }
}
