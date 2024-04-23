package com.KacFlor.ShopSpring.service;

import com.KacFlor.ShopSpring.model.CardData;
import com.KacFlor.ShopSpring.dao.CardDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
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
