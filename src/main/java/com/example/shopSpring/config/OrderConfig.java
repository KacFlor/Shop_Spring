package com.example.shopSpring.config;

import com.example.shopSpring.dao.OrderItemRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrderConfig {

    public OrderConfig(){

    }

    @Bean
    CommandLineRunner commandLineRunner10(OrderItemRepository orderItemRepository) {
        return (args) -> {
        };
    }
}