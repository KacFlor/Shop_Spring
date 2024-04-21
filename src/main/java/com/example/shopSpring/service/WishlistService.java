package com.example.shopSpring.service;

import com.example.shopSpring.model.Wishlist;
import com.example.shopSpring.dao.WishlistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishlistService {

    private final WishlistRepository wishlistRepository;

    @Autowired
    public WishlistService(WishlistRepository wishlistRepository) {
        this.wishlistRepository = wishlistRepository;
    }

    public List<Wishlist> getWishlist() {
        return wishlistRepository.findAll();
    }
}
