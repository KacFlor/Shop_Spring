package com.example.shopSpring.controller;

import java.util.List;

import com.example.shopSpring.model.CardData;
import com.example.shopSpring.service.CardDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(
        path = {"carddata"}
)
public class CardDataController {

    private final CardDataService cardDataService;

    @Autowired
    public CardDataController(CardDataService cardDataService) {
        this.cardDataService = cardDataService;
    }

    @GetMapping
    public List<CardData> getCardData() {
        return this.cardDataService.getCardData();
    }

}
