package com.example.shopSpring.config;

import com.example.shopSpring.dao.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomerConfig {

    public CustomerConfig(){
    }

    @Bean
    CommandLineRunner commandLineRunner11(CustomerRepository customerRepository) {
        return (args) -> {
        };
    }
}