package com.KacFlor.ShopSpring.config;

import com.KacFlor.ShopSpring.dao.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserConfig{

    public UserConfig(){

    }

    @Bean
    CommandLineRunner commandLineRunnerUser(UserRepository userRepository){
        return (args) -> {
        };
    }
}