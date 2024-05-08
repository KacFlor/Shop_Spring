package com.KacFlor.ShopSpring.service;

import com.KacFlor.ShopSpring.config.ExceptionsConfig;
import com.KacFlor.ShopSpring.controllersRequests.NewPromotion;
import com.KacFlor.ShopSpring.dao.PromotionRepository;
import com.KacFlor.ShopSpring.model.Promotion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PromotionService{

    private final PromotionRepository promotionRepository;

    @Autowired
    public PromotionService(PromotionRepository promotionRepository){
        this.promotionRepository = promotionRepository;
    }

    public List<Promotion> getAll(){
        return promotionRepository.findAll();
    }

    public Promotion getById(Integer Id){

        Optional<Promotion> optionalPromotion = promotionRepository.findById(Id);
        if(optionalPromotion.isPresent()){
            return optionalPromotion.get();
        }
        else{
            throw new ExceptionsConfig.ResourceNotFoundException("Promotion not found");
        }
    }

    public boolean deleteById(Integer Id){

        Optional<Promotion> optionalPromotion = promotionRepository.findById(Id);
        if(optionalPromotion.isPresent()){
            Promotion promotion = optionalPromotion.get();

            promotionRepository.delete(promotion);

            return true;
        }
        else{
            throw new ExceptionsConfig.ResourceNotFoundException("Promotion not found");
        }
    }

    public boolean updatePromotion(NewPromotion newPromotion, Integer Id){
        Optional<Promotion> optionalPromotion = promotionRepository.findById(Id);

        if(optionalPromotion.isPresent()){
            Promotion promotion = optionalPromotion.get();
            promotion.setName(newPromotion.getName());
            promotion.setDescription(newPromotion.getDescription());
            promotion.setStartDate(newPromotion.getStartDate());
            promotion.setEndDate(newPromotion.getEndDate());
            promotion.setDiscount(newPromotion.getDiscount());

            promotionRepository.save(promotion);

            return true;
        }
        else{
            throw new ExceptionsConfig.ResourceNotFoundException("Promotion not found");
        }
    }

    public boolean addNewPromotion(NewPromotion newPromotion){

        Promotion promotion = new Promotion();

        promotion.setName(newPromotion.getName());
        promotion.setDescription(newPromotion.getDescription());
        promotion.setStartDate(newPromotion.getStartDate());
        promotion.setEndDate(newPromotion.getEndDate());
        promotion.setDiscount(newPromotion.getDiscount());

        promotionRepository.save(promotion);

        return true;

    }
}
