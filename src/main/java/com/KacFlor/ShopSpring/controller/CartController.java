package com.KacFlor.ShopSpring.controller;

import java.util.List;
import java.util.Optional;

import com.KacFlor.ShopSpring.model.Role;
import com.KacFlor.ShopSpring.service.CartService;
import com.KacFlor.ShopSpring.model.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(
        path = {"carts"}
)
public class CartController{

    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService){
        this.cartService = cartService;
    }

    @PreAuthorize("hasAuthority('" + Role.Fields.ADMIN + "')")
    @GetMapping
    public List<Cart> getAll(){
        return this.cartService.getAll();
    }

    @PreAuthorize("hasAuthority('" + Role.Fields.ADMIN + "')")
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Cart>> getById(@PathVariable("id") Integer id){
        Optional<Cart> cart = Optional.ofNullable(cartService.getById(id));
        return ResponseEntity.ok(cart);
    }

    @PreAuthorize("hasAnyAuthority('" + Role.Fields.USER + "', '" + Role.Fields.ADMIN + "')")
    @GetMapping("/customer")
    public ResponseEntity<Optional<Cart>> getByCustomerId(@RequestParam("Cid") Integer Cid){
        Optional<Cart> cart = Optional.ofNullable(cartService.getCartByCustomerId(Cid));
        return ResponseEntity.ok(cart);
    }
}
