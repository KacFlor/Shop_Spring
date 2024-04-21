package com.example.shopSpring.service;

import com.example.shopSpring.model.Promotion;
import com.example.shopSpring.dao.PromotionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PromotionService {

    private final PromotionRepository promotionRepository;

    @Autowired
    public PromotionService(PromotionRepository promotionRepository) {
        this.promotionRepository = promotionRepository;
    }

    public List<Promotion> getPromotion() {
        return promotionRepository.findAll();
    }
}
