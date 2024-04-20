package com.example.demo.OrderItem;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrderItemConfig {

    public OrderItemConfig(){

    }

    @Bean
    CommandLineRunner commandLineRunner() {
        return (args) -> {

        };
    }
}