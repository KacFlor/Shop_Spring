package com.KacFlor.ShopSpring.developerHelper;

import com.KacFlor.ShopSpring.dao.WishlistRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WishlistConfig{

    public WishlistConfig(){

    }

    @Bean
    CommandLineRunner commandLineRunnerWhishlist(WishlistRepository wishlistRepository){
        return (args) -> {
        };

    }
}