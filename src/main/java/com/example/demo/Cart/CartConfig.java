package com.example.demo.Cart;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CartConfig {

    public CartConfig(){

    }

    @Bean
    CommandLineRunner commandLineRunner() {
        return (args) -> {

        };
    }
}
