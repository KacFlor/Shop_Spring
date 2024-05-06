package com.KacFlor.ShopSpring.service;

import com.KacFlor.ShopSpring.config.ExceptionsConfig;
import com.KacFlor.ShopSpring.dao.OrderItemRepository;
import com.KacFlor.ShopSpring.model.CardData;
import com.KacFlor.ShopSpring.model.OrderItem;
import com.KacFlor.ShopSpring.model.Shipment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderItemService{

    private final OrderItemRepository orderItemRepository;

    @Autowired
    public OrderItemService(OrderItemRepository orderItemRepository){
        this.orderItemRepository = orderItemRepository;
    }

    public List<OrderItem> getAll(){
        return orderItemRepository.findAll();
    }

    public OrderItem getOrderItemById(Integer Id){

        Optional<OrderItem> optionalOrderItem = orderItemRepository.findById(Id);
        if(optionalOrderItem.isPresent()){
            return optionalOrderItem.get();
        }
        else{
            throw new ExceptionsConfig.ResourceNotFoundException("CardData not found");
        }
    }

    public boolean deleteById(Integer Id){

        Optional<OrderItem> optionalOrderItem = orderItemRepository.findById(Id);
        if(optionalOrderItem.isPresent()){
            OrderItem orderItem = optionalOrderItem.get();

            orderItemRepository.delete(orderItem);

            return true;
        }
        else{
            throw new ExceptionsConfig.ResourceNotFoundException("Shipment not found");
        }
    }
}
