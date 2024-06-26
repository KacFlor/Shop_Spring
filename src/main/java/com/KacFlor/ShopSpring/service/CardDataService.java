package com.KacFlor.ShopSpring.service;

import com.KacFlor.ShopSpring.Exceptions.ExceptionsConfig;
import com.KacFlor.ShopSpring.controllersRequests.NewCardData;
import com.KacFlor.ShopSpring.dao.CustomerRepository;
import com.KacFlor.ShopSpring.model.CardData;
import com.KacFlor.ShopSpring.dao.CardDataRepository;
import com.KacFlor.ShopSpring.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CardDataService{

    private final CardDataRepository cardDataRepository;

    private final CustomerRepository customerRepository;

    @Autowired
    public CardDataService(CustomerRepository customerRepository, CardDataRepository cardDataRepository){
        this.cardDataRepository = cardDataRepository;
        this.customerRepository = customerRepository;
    }

    public List<CardData> getAll(){
        return cardDataRepository.findAll();
    }

    public CardData getCardDataById(Integer Id){

        Optional<CardData> optionalCardData = cardDataRepository.findById(Id);

        if (optionalCardData.isEmpty()) {
            throw new ExceptionsConfig.ResourceNotFoundException("CardData not found");
        }

        return optionalCardData.get();

    }

    public boolean updateCardData(NewCardData newCardData, Integer CDid, Integer Cid){

        Optional<Customer> optionalCustomer = customerRepository.findById(Cid);
        Optional<CardData> optionalCardData = cardDataRepository.findById(CDid);

        if (optionalCustomer.isEmpty()) {
            throw new ExceptionsConfig.ResourceNotFoundException("Customer not found");
        }

        if (optionalCardData.isEmpty()) {
            throw new ExceptionsConfig.ResourceNotFoundException("CardData not found");
        }

        if(Objects.equals(optionalCardData.get().getCustomer().getId(), Cid)){

            CardData cardData = optionalCardData.get();
            cardData.setCardNum(newCardData.getCardNum());

            cardDataRepository.save(cardData);

            return true;
        }
        else{
            throw new ExceptionsConfig.ResourceNotFoundException("CardData not found for that Customer");
        }

    }

    public boolean deleteCardData(Integer CDid, Integer Cid){

        Optional<Customer> optionalCustomer = customerRepository.findById(Cid);
        Optional<CardData> optionalCardData = cardDataRepository.findById(CDid);

        if (optionalCustomer.isEmpty()) {
            throw new ExceptionsConfig.ResourceNotFoundException("Customer not found");
        }

        if (optionalCardData.isEmpty()) {
            throw new ExceptionsConfig.ResourceNotFoundException("CardData not found");
        }

        if (Objects.equals(optionalCardData.get().getCustomer().getId(), Cid)){

            Customer customer = optionalCustomer.get();
            CardData cardData = optionalCardData.get();

            customer.getCardDatas().remove(cardData);
            cardDataRepository.delete(cardData);
            customerRepository.save(customer);

            return true;
        }
        else{
            throw new ExceptionsConfig.ResourceNotFoundException("CardData not found for that Customer");
        }
    }

}
