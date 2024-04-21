package com.example.shopSpring.service;

import com.example.shopSpring.model.CardData;
import com.example.shopSpring.dao.CardDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CardDataService {

    private final CardDataRepository cardDataRepository;

    @Autowired
    public CardDataService(CardDataRepository cardDataRepository) {
        this.cardDataRepository = cardDataRepository;
    }

    public List<CardData> getCardData() {
        return cardDataRepository.findAll();
    }
}
