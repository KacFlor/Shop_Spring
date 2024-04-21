package com.example.shopSpring.config;

import com.example.shopSpring.dao.CategoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CategoryConfig {

    public CategoryConfig(){

    }

    @Bean
    CommandLineRunner commandLineRunner12(CategoryRepository categoryRepository) {
        return (args) -> {
        };
    }
}