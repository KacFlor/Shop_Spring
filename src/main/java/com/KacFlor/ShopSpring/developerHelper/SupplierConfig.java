package com.KacFlor.ShopSpring.developerHelper;

import com.KacFlor.ShopSpring.dao.SupplierRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SupplierConfig{

    public SupplierConfig(){

    }

    @Bean
    CommandLineRunner commandLineRunner3(SupplierRepository supplierRepository){
        return (args) -> {
        };
    }
}