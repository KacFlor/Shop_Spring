package com.KacFlor.ShopSpring.service;

import com.KacFlor.ShopSpring.dao.ReviewRepository;
import com.KacFlor.ShopSpring.model.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public List<Review> getReview() {
        return reviewRepository.findAll();
    }
}
