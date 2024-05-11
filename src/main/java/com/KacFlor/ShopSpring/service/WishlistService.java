package com.KacFlor.ShopSpring.service;

import com.KacFlor.ShopSpring.config.ExceptionsConfig;
import com.KacFlor.ShopSpring.dao.CustomerRepository;
import com.KacFlor.ShopSpring.dao.WishlistRepository;
import com.KacFlor.ShopSpring.model.Customer;
import com.KacFlor.ShopSpring.model.Wishlist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WishlistService{

    private final WishlistRepository wishlistRepository;

    private final CustomerRepository customerRepository;

    @Autowired
    public WishlistService(CustomerRepository customerRepository, WishlistRepository wishlistRepository){
        this.wishlistRepository = wishlistRepository;
        this.customerRepository = customerRepository;
    }

    public List<Wishlist> getAll(){
        return wishlistRepository.findAll();
    }

    public Wishlist getById(Integer Id){

        Optional<Wishlist> optionalWishlist = wishlistRepository.findById(Id);

        if (optionalWishlist.isEmpty()) {
            throw new ExceptionsConfig.ResourceNotFoundException("Wishlist not found");
        }

        return optionalWishlist.get();

    }

    public Wishlist getCartByCustomerId(Integer Id){

        Optional<Customer> optionalCustomer = customerRepository.findById(Id);

        if (optionalCustomer.isEmpty()) {
            throw new ExceptionsConfig.ResourceNotFoundException("Customer not found");
        }

        return wishlistRepository.findByCustomerId(Id);

    }

}
