package com.example.shopSpring.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
public class Product {

    @Id
    private Integer id;

    private String sku;

    private String description;

    private Double price;

    private Integer stock;

    public Product() {
    }

    public Product(Integer id, String sku, String description, Double price, Integer stock) {
        this.id = id;
        this.sku = sku;
        this.description = description;
        this.price = price;
        this.stock = stock;
    }
}
