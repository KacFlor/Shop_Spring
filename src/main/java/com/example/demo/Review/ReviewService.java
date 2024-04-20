package com.example.demo.Review;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {

    @Autowired
    public ReviewService() {

    }

    public List<Review> getReview() {return List;}
}
