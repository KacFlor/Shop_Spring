package com.example.shopSpring.controller;

import java.util.List;

import com.example.shopSpring.model.Wishlist;
import com.example.shopSpring.service.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(
        path = {"wishlist"}
)
public class WishlistController {

    private final WishlistService wishlistService;

    @Autowired
    public WishlistController(WishlistService wishlistService) {
        this.wishlistService = wishlistService;
    }

    @GetMapping
    public List<Wishlist> getWishlist() {
        return this.wishlistService.getWishlist();
    }
}
