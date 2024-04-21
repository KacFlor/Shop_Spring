package com.example.shopSpring.service;

import com.example.shopSpring.model.Cart;
import com.example.shopSpring.dao.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

    private final CartRepository cartRepository;

    @Autowired
    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public List<Cart> getCart() {
        return cartRepository.findAll();
    }
}
