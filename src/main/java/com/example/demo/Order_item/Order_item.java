package com.example.demo.Order_item;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.time.LocalDate;

@Entity
@Table
public class Order_item {
    private Integer id;
    private LocalDate quantity;
    private Double price;

    public Order_item() {
    }

    public Order_item(Integer id, LocalDate quantity, Double price) {
        this.id = id;
        this.quantity = quantity;
        this.price = price;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getQuantity() {
        return quantity;
    }

    public void setQuantity(LocalDate quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
