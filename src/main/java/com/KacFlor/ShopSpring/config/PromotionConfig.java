package com.KacFlor.ShopSpring.config;

import com.KacFlor.ShopSpring.dao.PromotionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PromotionConfig{

    public PromotionConfig(){
    }

    @Bean
    CommandLineRunner commandLineRunnerPromotion(PromotionRepository promotionRepository){
        return (args) -> {
        };
    }
}