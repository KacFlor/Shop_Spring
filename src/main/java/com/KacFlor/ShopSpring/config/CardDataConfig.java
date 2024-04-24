package com.KacFlor.ShopSpring.config;

import com.KacFlor.ShopSpring.dao.CardDataRepository;
import com.KacFlor.ShopSpring.model.CardData;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class CardDataConfig{

    public CardDataConfig(){
    }

    @Bean
    CommandLineRunner commandLineRunner14(CardDataRepository cardDataRepository){
        return (args) -> {
            CardData first = new CardData(
                    "123 123 132 3121"
            );
            cardDataRepository.saveAll(
                    List.of(first)
            );
        };

    }
}
