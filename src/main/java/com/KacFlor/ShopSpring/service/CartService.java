package com.KacFlor.ShopSpring.service;

import com.KacFlor.ShopSpring.config.ExceptionsConfig;
import com.KacFlor.ShopSpring.dao.CustomerRepository;
import com.KacFlor.ShopSpring.model.Cart;
import com.KacFlor.ShopSpring.dao.CartRepository;
import com.KacFlor.ShopSpring.model.Category;
import com.KacFlor.ShopSpring.model.Customer;
import com.KacFlor.ShopSpring.model.Shipment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartService{

    private final CartRepository cartRepository;

    private final CustomerRepository customerRepository;

    @Autowired
    public CartService(CartRepository cartRepository, CustomerRepository customerRepository){
        this.cartRepository = cartRepository;
        this.customerRepository = customerRepository;
    }

    public List<Cart> getAll(){
        return cartRepository.findAll();
    }

    public Cart getById(Integer Id){

        Optional<Cart> optionalCart = cartRepository.findById(Id);

        if (optionalCart.isEmpty()) {
            throw new ExceptionsConfig.ResourceNotFoundException("Cart not found");
        }

        return optionalCart.get();

    }

    public Cart getCartByCustomerId(Integer Id){

        Optional<Customer> optionalCustomer = customerRepository.findById(Id);

        if (optionalCustomer.isEmpty()) {
            throw new ExceptionsConfig.ResourceNotFoundException("Customer not found");
        }

        return cartRepository.findByCustomerId(Id);

    }
}
