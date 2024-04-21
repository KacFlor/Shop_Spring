package com.KacFlor.ShopSpring.service;

import com.KacFlor.ShopSpring.model.Cart;
import com.KacFlor.ShopSpring.dao.CartRepository;
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
