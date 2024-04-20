package com.example.demo.CardData;

import java.util.List;
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
    public List<CardData> getCardData() {return this.cardDataService.getCardData();}

}
