package com.KacFlor.ShopSpring.developerHelper;

import com.KacFlor.ShopSpring.dao.CartRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CartConfig{

    public CartConfig(){
    }

    @Bean
    CommandLineRunner commandLineRunnerCart(CartRepository cartRepository){
        return (args) -> {
        };
    }
}
