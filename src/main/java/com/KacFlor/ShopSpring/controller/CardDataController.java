package com.KacFlor.ShopSpring.controller;

import java.util.List;
import java.util.Optional;

import com.KacFlor.ShopSpring.controllersRequests.NewCardData;
import com.KacFlor.ShopSpring.model.Role;
import com.KacFlor.ShopSpring.service.CardDataService;
import com.KacFlor.ShopSpring.model.CardData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(
        path = {"cards-data"}
)
public class CardDataController{

    private final CardDataService cardDataService;

    @Autowired
    public CardDataController(CardDataService cardDataService){
        this.cardDataService = cardDataService;
    }

    @PreAuthorize("hasAuthority('" + Role.Fields.ADMIN + "')")
    @GetMapping
    public ResponseEntity<List<CardData>> getAll(){
        List<CardData> cardData = cardDataService.getAll();
        return ResponseEntity.ok(cardData);
    }

    @PreAuthorize("hasAnyAuthority('" + Role.Fields.USER + "', '" + Role.Fields.ADMIN + "')")
    @GetMapping("/{id}")
    public ResponseEntity<Optional<CardData>> getById(@PathVariable("id") Integer id){
        Optional<CardData> cardData = Optional.ofNullable(cardDataService.getCardDataById(id));
        return ResponseEntity.ok(cardData);
    }

    @PreAuthorize("hasAnyAuthority('" + Role.Fields.USER + "', '" + Role.Fields.ADMIN + "')")
    @PatchMapping("/{id}/customer")
    public ResponseEntity<?> dataChangeByCustomerId(@RequestBody NewCardData newCardData, @PathVariable("id") Integer cardDataId, @RequestParam("Cid") Integer customerId){
        cardDataService.updateCardData(newCardData, cardDataId, customerId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('" + Role.Fields.USER + "', '" + Role.Fields.ADMIN + "')")
    @DeleteMapping("/{id}/customer")
    public ResponseEntity<?> deleteByCustomerId(@PathVariable("id") Integer cardDataId, @RequestParam("Cid") Integer customerId){
        cardDataService.deleteCardData(cardDataId, customerId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
