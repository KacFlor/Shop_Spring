package com.example.demo.Product;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
public class Product {

    private Integer id;
    private String SKU;
    private  String description;
    private Double price;
    private Integer stock;

    public Product() {
    }

    public Product(Integer id, String SKU, String description, Double price, Integer stock) {
        this.id = id;
        this.SKU = SKU;
        this.description = description;
        this.price = price;
        this.stock = stock;
    }
}
