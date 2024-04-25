package com.KacFlor.ShopSpring.config;

import com.KacFlor.ShopSpring.dao.PaymentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PaymentConfig{

    public PaymentConfig(){

    }

    @Bean
    CommandLineRunner commandLineRunnerPayment(PaymentRepository paymentRepository){
        return (args) -> {
        };
    }
}