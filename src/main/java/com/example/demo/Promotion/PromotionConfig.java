package com.example.demo.Promotion;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PromotionConfig {

    public PromotionConfig(){

    }

    @Bean
    CommandLineRunner commandLineRunner() {
        return (args) -> {

        };
    }
}