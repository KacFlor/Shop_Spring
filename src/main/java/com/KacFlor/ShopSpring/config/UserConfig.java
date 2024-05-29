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

            cart.setCustomer(customer);
            wishlist.setCustomer(customer);
            customer.setCart(cart);
            customer.setWishlist(wishlist);

            User HeadAdmin = new User(
                    "HAdmin",
                    encoder.encode("1234"),
                    customer,
                    Role.ADMIN
            );

            customer.setUser(HeadAdmin);

            userRepository.save(HeadAdmin);
            customerRepository.save(customer);
        };
    }


}