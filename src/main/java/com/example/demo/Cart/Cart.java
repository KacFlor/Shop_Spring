package com.example.demo.Cart;

import com.example.demo.Product.Product;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table
@Getter
@Setter
public class Cart {

    private Integer id;
    private List<Product> cartItems;

    public Cart() {
    }

    public Cart(Integer id, List<Product> cartItems) {
        this.id = id;
        this.cartItems = cartItems;
    }
}
