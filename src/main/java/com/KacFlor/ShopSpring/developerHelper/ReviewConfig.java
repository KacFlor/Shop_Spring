package com.KacFlor.ShopSpring.developerHelper;

import com.KacFlor.ShopSpring.dao.ReviewRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ReviewConfig{

    public ReviewConfig(){

    }

    @Bean
    CommandLineRunner commandLineRunnerReview(ReviewRepository reviewRepository){
        return (args) -> {
        };
    }
}