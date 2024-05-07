package com.KacFlor.ShopSpring.service;

import com.KacFlor.ShopSpring.config.ExceptionsConfig;
import com.KacFlor.ShopSpring.controllersRequests.NewOrderItem;
import com.KacFlor.ShopSpring.controllersRequests.NewShipment;
import com.KacFlor.ShopSpring.dao.OrderItemRepository;
import com.KacFlor.ShopSpring.dao.OrderRepository;
import com.KacFlor.ShopSpring.dao.ProductRepository;
import com.KacFlor.ShopSpring.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService{

    private final ProductRepository productRepository;

    private final OrderItemRepository orderItemRepository;

    private final OrderRepository orderRepository;

    @Autowired
    public ProductService(ProductRepository productRepository, OrderItemRepository orderItemRepository, OrderRepository orderRepository){
        this.productRepository = productRepository;
        this.orderItemRepository = orderItemRepository;
        this.orderRepository = orderRepository;
    }

    public List<Product> getProduct(){
        return productRepository.findAll();
    }

    public boolean addOrderItem(NewOrderItem newOrderItem, Integer Oid){
        Optional<Order> optionalOrder = orderRepository.findById(Oid);

        if(optionalOrder.isPresent()){
            Order order = optionalOrder.get();
            OrderItem orderItem = new OrderItem(newOrderItem.getName(), newOrderItem.getQuantity(), newOrderItem.getPrice());

            orderItem.setOrder(order);
            orderItemRepository.save(orderItem);

            order.getOrderItems().add(orderItem);
            orderRepository.save(order);

            return true;
        }
        else{
            throw new ExceptionsConfig.ResourceNotFoundException("Customer not found");
        }
    }
}
