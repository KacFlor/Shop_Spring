package com.example.shopSpring.config;

import com.example.shopSpring.dao.PromotionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PromotionConfig {

    public PromotionConfig(){
    }

    @Bean
    CommandLineRunner commandLineRunner6(PromotionRepository promotionRepository) {
        return (args) -> {
        };
    }
}