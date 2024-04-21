package com.example.shopSpring.config;

import com.example.shopSpring.dao.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProductConfig {

    public ProductConfig() {
    }

    @Bean
    CommandLineRunner commandLineRunner7(ProductRepository productRepository) {
        return (args) -> {
        };
    }
}