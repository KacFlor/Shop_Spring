package com.KacFlor.ShopSpring.controller;

import java.util.List;

import com.KacFlor.ShopSpring.service.CartService;
import com.KacFlor.ShopSpring.model.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(
        path = {"cart"}
)
public class CartController{

    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService){
        this.cartService = cartService;
    }

    @GetMapping
    public List<Cart> getCart(){
        return this.cartService.getCart();
    }
}
