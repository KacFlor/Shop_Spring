package com.KacFlor.ShopSpring.controller;

import java.util.List;

import com.KacFlor.ShopSpring.service.CardDataService;
import com.KacFlor.ShopSpring.model.CardData;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(
        path = {"carddata"}
)
public class CardDataController{

    private final CardDataService cardDataService;

    @Autowired
    public CardDataController(CardDataService cardDataService){
        this.cardDataService = cardDataService;
    }

    @GetMapping
    public List<CardData> getCardData(){
        return this.cardDataService.getCardData();
    }

}
