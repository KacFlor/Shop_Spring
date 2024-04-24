package com.KacFlor.ShopSpring.service;

import com.KacFlor.ShopSpring.dao.WishlistRepository;
import com.KacFlor.ShopSpring.model.Wishlist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishlistService{

    private final WishlistRepository wishlistRepository;

    @Autowired
    public WishlistService(WishlistRepository wishlistRepository){
        this.wishlistRepository = wishlistRepository;
    }

    public List<Wishlist> getWishlist(){
        return wishlistRepository.findAll();
    }
}
