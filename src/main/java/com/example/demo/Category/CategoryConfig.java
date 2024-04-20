package com.example.demo.Category;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CategoryConfig {

    public CategoryConfig(){

    }

    @Bean
    CommandLineRunner commandLineRunner() {
        return (args) -> {

        };
    }
}