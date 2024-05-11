package com.KacFlor.ShopSpring.config;

import com.KacFlor.ShopSpring.dao.CartRepository;
import com.KacFlor.ShopSpring.dao.CustomerRepository;
import com.KacFlor.ShopSpring.dao.UserRepository;
import com.KacFlor.ShopSpring.dao.WishlistRepository;
import com.KacFlor.ShopSpring.model.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


import java.util.List;

@Configuration
public class UserConfig{

    public UserConfig(){

    }

    @Bean
    CommandLineRunner commandLineRunnerUser(WishlistRepository wishlistRepository, CartRepository cartRepository , UserRepository userRepository, CustomerRepository customerRepository){
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        return (args) -> {
            Customer customer = new Customer("HAdmin");
            Cart cart = new Cart();
            Wishlist wishlist = new Wishlist();

            cartRepository.save(cart);
            wishlistRepository.save(wishlist);

            customer.setCart(cart);
            customer.setWishlist(wishlist);

            User HeadAdmin = new User(
                    "HAdmin",
                    encoder.encode("1234"),
                    customer,
                    Role.ADMIN
            );
            userRepository.saveAll(
                    List.of(HeadAdmin)
            );
            customer.setUser(HeadAdmin);
            customerRepository.save(customer);
            cart.setCustomer(customer);
            cartRepository.save(cart);
            wishlist.setCustomer(customer);
            wishlistRepository.save(wishlist);
        };
    }

}