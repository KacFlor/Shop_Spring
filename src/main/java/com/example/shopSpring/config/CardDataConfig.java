package com.example.shopSpring.config;

import com.example.shopSpring.dao.CardDataRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CardDataConfig {

    public CardDataConfig() {
    }

    @Bean
    CommandLineRunner commandLineRunner14(CardDataRepository cardDataRepository) {
        return (args) -> {
        };
    }
}
