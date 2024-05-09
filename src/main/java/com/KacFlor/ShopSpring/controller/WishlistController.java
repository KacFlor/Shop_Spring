package com.KacFlor.ShopSpring.controller;

import java.util.List;
import java.util.Optional;

import com.KacFlor.ShopSpring.model.Cart;
import com.KacFlor.ShopSpring.model.Role;
import com.KacFlor.ShopSpring.model.Wishlist;
import com.KacFlor.ShopSpring.service.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(
        path = {"wishlists"}
)
public class WishlistController{

    private final WishlistService wishlistService;

    @Autowired
    public WishlistController(WishlistService wishlistService){
        this.wishlistService = wishlistService;
    }

    @PreAuthorize("hasAuthority('" + Role.Fields.ADMIN + "')")
    @GetMapping
    public List<Wishlist> getAll(){
        return this.wishlistService.getAll();
    }

    @PreAuthorize("hasAuthority('" + Role.Fields.ADMIN + "')")
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Wishlist>> getById(@PathVariable("id") Integer id){
        Optional<Wishlist> wishlist = Optional.ofNullable(wishlistService.getById(id));
        return ResponseEntity.ok(wishlist);
    }

    @PreAuthorize("hasAnyAuthority('" + Role.Fields.USER + "', '" + Role.Fields.ADMIN + "')")
    @GetMapping("/customer/{id}")
    public ResponseEntity<Optional<Wishlist>> getByCustomerId(@PathVariable("id") Integer id){
        Optional<Wishlist> wishlist = Optional.ofNullable(wishlistService.getCartByCustomerId(id));
        return ResponseEntity.ok(wishlist);
    }
}
