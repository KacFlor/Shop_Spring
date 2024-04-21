package com.example.shopSpring.config;

import com.example.shopSpring.dao.ShipmentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ShipmentConfig {

    public ShipmentConfig(){

    }

    @Bean
    CommandLineRunner commandLineRunner4(ShipmentRepository shipmentRepository) {
        return (args) -> {
        };
    }
}