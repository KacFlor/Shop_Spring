package com.KacFlor.ShopSpring.developerHelper;

import com.KacFlor.ShopSpring.dao.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomerConfig{

    public CustomerConfig(){
    }

    @Bean
    CommandLineRunner commandLineRunnerCustomer(CustomerRepository customerRepository){
        return (args) -> {
        };
    }
}