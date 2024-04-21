package com.example.shopSpring.config;

import com.example.shopSpring.dao.WishlistRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WishlistConfig {

    public WishlistConfig(){

    }

    @Bean
    CommandLineRunner commandLineRunner(WishlistRepository wishlistRepository) {
        return (args) -> {
        };

    }
}