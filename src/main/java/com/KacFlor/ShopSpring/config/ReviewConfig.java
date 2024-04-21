package com.KacFlor.ShopSpring.config;

import com.KacFlor.ShopSpring.dao.ReviewRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ReviewConfig {

    public ReviewConfig(){

    }

    @Bean
    CommandLineRunner commandLineRunner5(ReviewRepository reviewRepository) {
        return (args) -> {
        };
    }
}