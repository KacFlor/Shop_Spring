package com.example.demo.CardData;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardDataService {

    @Autowired
    public CardDataService() {

    }

    public List<CardData> getCardData() {return List.of();}
}
