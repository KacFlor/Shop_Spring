package com.KacFlor.ShopSpring.developerHelper;

import com.KacFlor.ShopSpring.dao.OrderItemRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrderItemConfig{

    public OrderItemConfig(){
    }

    @Bean
    CommandLineRunner commandLineRunnerOrderItem(OrderItemRepository orderItemRepository){
        return (args) -> {
        };
    }
}