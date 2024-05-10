package com.KacFlor.ShopSpring.developerHelper;

import com.KacFlor.ShopSpring.dao.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProductConfig{

    public ProductConfig(){
    }

    @Bean
    CommandLineRunner commandLineRunnerProduct(ProductRepository productRepository){
        return (args) -> {
        };
    }
}