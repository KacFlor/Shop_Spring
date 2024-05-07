package com.KacFlor.ShopSpring.service;


import com.KacFlor.ShopSpring.config.ExceptionsConfig;
import com.KacFlor.ShopSpring.controllersRequests.NewOrderItem;
import com.KacFlor.ShopSpring.dao.*;
import com.KacFlor.ShopSpring.model.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest{

    @Mock
    private ProductRepository productRepository;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderItemRepository orderItemRepository;

    @InjectMocks
    private ProductService productService;

    @DisplayName("JUnit test for addOrderItem method")
    @Test
    public void testAddOrderItem(){
        Integer orderId = 1;

        Order order = new Order();
        order.setId(orderId);
        order.setOrderItems(new ArrayList<>());

        NewOrderItem newOrderItem = new NewOrderItem();
        newOrderItem.setName("Test1");
        newOrderItem.setQuantity(22.00);
        newOrderItem.setPrice(250.0);

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

        boolean result = productService.addOrderItem(newOrderItem, orderId);
        assertTrue(result);

        verify(orderRepository, times(1)).findById(orderId);
        verify(orderItemRepository, times(1)).save(any(OrderItem.class));
        verify(orderRepository, times(1)).save(any(Order.class));

        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        assertThrows(ExceptionsConfig.ResourceNotFoundException.class, () -> productService.addOrderItem(newOrderItem, orderId));

        verify(orderRepository, times(2)).findById(orderId);
    }

}
