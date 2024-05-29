package com.KacFlor.ShopSpring.service;

import com.KacFlor.ShopSpring.Exceptions.ExceptionsConfig;
import com.KacFlor.ShopSpring.controllersRequests.NewItem;
import com.KacFlor.ShopSpring.dao.OrderItemRepository;
import com.KacFlor.ShopSpring.dao.OrderRepository;
import com.KacFlor.ShopSpring.dao.ProductRepository;
import com.KacFlor.ShopSpring.model.Order;
import com.KacFlor.ShopSpring.model.OrderItem;
import com.KacFlor.ShopSpring.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderItemService{

    private final OrderItemRepository orderItemRepository;

    private final OrderRepository orderRepository;

    private final ProductRepository productRepository;

    @Autowired
    public OrderItemService(OrderItemRepository orderItemRepository, OrderRepository orderRepository, ProductRepository productRepository){
        this.orderItemRepository = orderItemRepository;
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    public List<OrderItem> getAll(){
        return orderItemRepository.findAll();
    }

    public OrderItem getOrderItemById(Integer Id){

        Optional<OrderItem> optionalOrderItem = orderItemRepository.findById(Id);

        if (optionalOrderItem.isEmpty()) {
            throw new ExceptionsConfig.ResourceNotFoundException("OrderItem not found");
        }

        return optionalOrderItem.get();

    }

    public boolean deleteById(NewItem newItem, Integer Id, Integer Oid, Integer Pid){

        Optional<OrderItem> optionalOrderItem = orderItemRepository.findById(Id);
        Optional<Order> optionalOrder = orderRepository.findById(Oid);
        Optional<Product> optionalProduct = productRepository.findById(Pid);


        if (optionalOrderItem.isEmpty()) {
            throw new ExceptionsConfig.ResourceNotFoundException("OrderItem not found");
        }

        if (optionalOrder.isEmpty()) {
            throw new ExceptionsConfig.ResourceNotFoundException("Order not found");
        }

        if (optionalProduct.isEmpty()) {
            throw new ExceptionsConfig.ResourceNotFoundException("Product not found");
        }

        OrderItem orderItem = optionalOrderItem.get();
        Order order = optionalOrder.get();
        Product product = optionalProduct.get();

        if(!orderItem.getName().equals(product.getName()))
        {
            throw new ExceptionsConfig.ResourceNotFoundException("Resource not found");
        }

        if(!order.getOrderItems().contains(orderItem))
        {
            throw new ExceptionsConfig.ResourceNotFoundException("Resource not found");
        }

        order.setTotalPrice(order.getTotalPrice() - orderItem.getPrice());

        orderItem.setQuantity(orderItem.getQuantity() - newItem.getQuantity());

        product.setStock(product.getStock() + newItem.getQuantity());

        orderRepository.save(order);

        if(orderItem.getQuantity() <= 0)
        {
            order.getOrderItems().remove(orderItem);
            orderItemRepository.delete(orderItem);
            product.getOrderItems().remove(orderItem);
        }

        productRepository.save(product);

        return true;

    }
}
