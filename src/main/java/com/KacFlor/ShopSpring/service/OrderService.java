package com.KacFlor.ShopSpring.service;

import com.KacFlor.ShopSpring.config.ExceptionsConfig;
import com.KacFlor.ShopSpring.controllersRequests.NewOrder;
import com.KacFlor.ShopSpring.model.Order;
import com.KacFlor.ShopSpring.dao.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService{

    final private OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository){
        this.orderRepository = orderRepository;
    }

    public List<Order> getAllOrders(){
        return orderRepository.findAll();
    }

    public Order getById(Integer Id){

        Optional<Order> optionalOrder = orderRepository.findById(Id);

        if(optionalOrder.isPresent()){
            return optionalOrder.get();
        }else{
            throw new ExceptionsConfig.ResourceNotFoundException("Order not found");
        }

    }

    public boolean updateOrder(NewOrder newOrder, Integer Id){

        Optional<Order> optionalOrder = orderRepository.findById(Id);

        if(optionalOrder.isPresent()){
            Order order = optionalOrder.get();

            order.setOrderDate(newOrder.getOrderDate());
            order.setTotalPrice(newOrder.getTotalPrice());

            orderRepository.save(order);

            return true;
        }
        else{
            throw new ExceptionsConfig.ResourceNotFoundException("Shipment not found");
        }

    }

    public boolean deleteById(Integer Id){

        Optional<Order> optionalOrder = orderRepository.findById(Id);
        if(optionalOrder.isPresent()){

            Order order = optionalOrder.get();

            orderRepository.delete(order);
            return true;

        }
        else{
            throw new ExceptionsConfig.ResourceNotFoundException("Shipment not found");
        }
    }


}
