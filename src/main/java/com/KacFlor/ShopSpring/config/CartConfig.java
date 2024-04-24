package com.KacFlor.ShopSpring.config;

import com.KacFlor.ShopSpring.dao.CartRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CartConfig{

    public CartConfig(){
    }

    @Bean
    CommandLineRunner commandLineRunner13(CartRepository cartRepository){
        return (args) -> {
        };
    }
}
