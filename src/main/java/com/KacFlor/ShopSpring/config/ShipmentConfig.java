package com.KacFlor.ShopSpring.config;

import com.KacFlor.ShopSpring.dao.ShipmentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ShipmentConfig{

    public ShipmentConfig(){

    }

    @Bean
    CommandLineRunner commandLineRunnerShipment(ShipmentRepository shipmentRepository){
        return (args) -> {
        };
    }
}