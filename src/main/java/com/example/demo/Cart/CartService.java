package com.example.demo.Cart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

    @Autowired
    public CartService() {

    }

    public List<Cart> getCart() {return List.of();}
}
