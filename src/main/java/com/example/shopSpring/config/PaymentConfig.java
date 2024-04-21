package com.example.shopSpring.config;

import com.example.shopSpring.dao.PaymentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PaymentConfig {

    public PaymentConfig(){

    }

    @Bean
    CommandLineRunner commandLineRunner8(PaymentRepository paymentRepository) {
        return (args) -> {
        };
    }
}