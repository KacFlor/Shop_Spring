package com.KacFlor.ShopSpring.developerHelper;

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
    CommandLineRunner commandLineRunnerCardData(CardDataRepository cardDataRepository){
        return (args) -> {

        };

    }
}
