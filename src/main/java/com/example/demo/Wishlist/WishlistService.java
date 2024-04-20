package com.example.demo.Wishlist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishlistService {

    @Autowired
    public WishlistService() {

    }

    public List<Wishlist> getWishlist() {return List;}
}
