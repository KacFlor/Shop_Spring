package com.example.demo.Cart;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table
public class Cart {
    private Integer id;
    private Iterable quantity;

    public Cart() {
    }

    public Cart(Integer id, Iterable quantity) {
        this.id = id;
        this.quantity = quantity;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Iterable getQuantity() {
        return quantity;
    }

    public void setQuantity(Iterable quantity) {
        this.quantity = quantity;
    }
}
