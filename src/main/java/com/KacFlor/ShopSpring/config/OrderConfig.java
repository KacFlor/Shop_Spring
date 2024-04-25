package com.KacFlor.ShopSpring.config;

import com.KacFlor.ShopSpring.dao.OrderItemRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrderConfig{

    public OrderConfig(){

    }

    @Bean
    CommandLineRunner commandLineRunnerOrder(OrderItemRepository orderItemRepository){
        return (args) -> {
        };
    }
}