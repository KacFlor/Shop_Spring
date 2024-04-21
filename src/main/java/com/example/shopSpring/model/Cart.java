package com.example.shopSpring.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table
@Getter
@Setter
public class Cart {

    @Id
    private Integer id;

    @OneToMany
    @JoinColumn
    private List<Product> cartItems;

    public Cart() {
    }

    public Cart(Integer id, List<Product> cartItems) {
        this.id = id;
        this.cartItems = cartItems;
    }
}
