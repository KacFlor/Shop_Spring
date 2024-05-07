package com.KacFlor.ShopSpring.controller;

import java.util.List;
import java.util.Optional;

import com.KacFlor.ShopSpring.controllersRequests.NewPromotion;
import com.KacFlor.ShopSpring.model.Promotion;
import com.KacFlor.ShopSpring.model.Role;
import com.KacFlor.ShopSpring.service.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(
        path = {"promotions"}
)
public class PromotionController{

    private final PromotionService promotionService;

    @Autowired
    public PromotionController(PromotionService promotionService){
        this.promotionService = promotionService;
    }

    @PreAuthorize("hasAnyAuthority('" + Role.Fields.USER + "', '" + Role.Fields.ADMIN + "')")
    @GetMapping
    public List<Promotion> getAll(){
        return this.promotionService.getAll();
    }

    @PreAuthorize("hasAnyAuthority('" + Role.Fields.USER + "', '" + Role.Fields.ADMIN + "')")
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Promotion>> getById(@PathVariable("id") Integer id){
        Optional<Promotion> promotion = Optional.ofNullable(promotionService.getById(id));
        return ResponseEntity.ok(promotion);
    }

    @PreAuthorize("hasAuthority('" + Role.Fields.ADMIN + "')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") Integer id){
        promotionService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('" + Role.Fields.ADMIN + "')")
    @PatchMapping("/{id}")
    public ResponseEntity<?> updatePromotion(@RequestBody NewPromotion newPromotion, @PathVariable("id") Integer id){
        promotionService.updatePromotion(newPromotion, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('" + Role.Fields.ADMIN + "')")
    @PostMapping("/new")
    public ResponseEntity<?> createNewPromotion(@RequestBody NewPromotion newPromotion){
        promotionService.addNewPromotion(newPromotion);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
