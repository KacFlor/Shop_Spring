package com.KacFlor.ShopSpring.controller;

import java.util.List;

import com.KacFlor.ShopSpring.model.Promotion;
import com.KacFlor.ShopSpring.service.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(
        path = {"promotion"}
)
public class PromotionController{

    private final PromotionService promotionService;

    @Autowired
    public PromotionController(PromotionService promotionService){
        this.promotionService = promotionService;
    }

    @GetMapping
    public List<Promotion> getPromotion(){
        return this.promotionService.getPromotion();
    }
}
