package com.KacFlor.ShopSpring.config;

import com.KacFlor.ShopSpring.dao.CustomerRepository;
import com.KacFlor.ShopSpring.dao.UserRepository;
import com.KacFlor.ShopSpring.model.Customer;
import com.KacFlor.ShopSpring.model.Role;
import com.KacFlor.ShopSpring.model.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


import java.util.List;

@Configuration
public class UserConfig{

    public UserConfig(){

    }

    @Bean
    CommandLineRunner commandLineRunnerUser(UserRepository userRepository, CustomerRepository customerRepository){
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        return (args) -> {
            Customer customer = new Customer("HAdmin");
            User HeadAdmin = new User(
                    "HAdmin",
                    encoder.encode("1234"),
                    customer,
                    Role.ADMIN
            );
            userRepository.saveAll(
                    List.of(HeadAdmin)
            );
            customer.setUser(HeadAdmin);
            customerRepository.save(customer);
        };
    }

}