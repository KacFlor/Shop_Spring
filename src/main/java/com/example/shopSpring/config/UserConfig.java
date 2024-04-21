package com.example.shopSpring.config;

import com.example.shopSpring.dao.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserConfig {

    public UserConfig(){

    }

    @Bean
    CommandLineRunner commandLineRunner2(UserRepository userRepository) {
        return (args) -> {
        };
    }
}