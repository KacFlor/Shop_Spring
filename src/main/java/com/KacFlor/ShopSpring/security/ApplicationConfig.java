package com.KacFlor.ShopSpring.security;

import com.KacFlor.ShopSpring.dao.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    private final UserRepository repository;
    @Bean
    public UserDetailsService userDetailsService(){
        return Username -> repository.findByLogin(Username)
                //idk what to do with that
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

}
