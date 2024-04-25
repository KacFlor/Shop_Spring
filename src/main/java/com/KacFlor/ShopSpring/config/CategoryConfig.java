package com.KacFlor.ShopSpring.config;

import com.KacFlor.ShopSpring.dao.CategoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CategoryConfig{

    public CategoryConfig(){

    }

    @Bean
    CommandLineRunner commandLineRunnerCategory(CategoryRepository categoryRepository){
        return (args) -> {
        };
    }
}