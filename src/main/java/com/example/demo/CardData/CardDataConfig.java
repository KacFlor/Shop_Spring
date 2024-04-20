package com.example.demo.CardData;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CardDataConfig {

    public CardDataConfig(){

    }

    @Bean
    CommandLineRunner commandLineRunner() {
        return (args) -> {

        };
    }
}
