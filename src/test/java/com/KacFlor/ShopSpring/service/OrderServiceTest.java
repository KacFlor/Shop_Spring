package com.KacFlor.ShopSpring.service;

import com.KacFlor.ShopSpring.config.ExceptionsConfig;
import com.KacFlor.ShopSpring.controllersRequests.NewOrder;
import com.KacFlor.ShopSpring.dao.OrderRepository;
import com.KacFlor.ShopSpring.model.Order;
import com.KacFlor.ShopSpring.model.OrderItem;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest{

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService orderService;

    @DisplayName("JUnit test for getAllOrders method")
    @Test
    public void testGetAllOrders(){
        Order order1 = new Order(LocalDate.of(2024, 5, 3), 2222.22);
        Order order2 = new Order(LocalDate.of(2025, 5, 3), 3333.33);
        Order order3 = new Order(LocalDate.of(2026, 5, 3), 4444.44);

        given(orderRepository.findAll()).willReturn(List.of(order1, order2, order3));

        List<Order> result = orderService.getAllOrders();

        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(3);

        assertThat(result.get(0).getTotalPrice()).isEqualTo(2222.22);
        assertThat(result.get(1).getTotalPrice()).isEqualTo(3333.33);
        assertThat(result.get(2).getTotalPrice()).isEqualTo(4444.44);

    }

    @DisplayName("JUnit test for getAllOrderItems method")
    @Test
    public void testGetAllOrderItems(){

        Integer orderId = 1;
        Order order = new Order();
        order.setId(orderId);
        order.setOrderItems(new ArrayList<>());
        List<OrderItem> orderItems = new ArrayList<>();
        OrderItem orderItem1 = new OrderItem("Test1", 2.0, 10.0);
        OrderItem orderItem2 = new OrderItem("Test2", 3.0, 15.0);
        orderItems.add(orderItem1);
        orderItems.add(orderItem2);
        order.getOrderItems().add(orderItem1);
        order.getOrderItems().add(orderItem2);

        Optional<Order> optionalOrder = Optional.of(order);

        when(orderRepository.findById(orderId)).thenReturn(optionalOrder);

        List<OrderItem> result = orderService.getAllOrderItems(orderId);
        assertEquals(orderItems, result);

        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        assertThrows(ExceptionsConfig.ResourceNotFoundException.class, () -> orderService.getAllOrderItems(orderId));

        verify(orderRepository, times(2)).findById(orderId);

    }

    @DisplayName("JUnit test for getById method")
    @Test
    public void testGetById(){

        Order order1 = new Order(LocalDate.of(2024, 5, 3), 2222.22);
        Integer orderId = 1;

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order1));

        Order order = orderService.getById(orderId);

        assertEquals(order1, order);

        verify(orderRepository, times(1)).findById(orderId);

        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        assertThrows(ExceptionsConfig.ResourceNotFoundException.class, () -> orderService.getById(orderId));

        verify(orderRepository, times(2)).findById(orderId);

    }

    @DisplayName("JUnit test for updateOrder method")
    @Test
    public void testUpdateOrder(){
        Integer orderId = 1;
        NewOrder newOrder = new NewOrder(LocalDate.of(2024, 5, 10), 100.0);

        Order existingOrder = new Order(LocalDate.of(2021, 1, 3), 140.0);

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(existingOrder));

        boolean result = orderService.updateOrder(newOrder, orderId);
        assertTrue(result);

        assertEquals(newOrder.getOrderDate(), existingOrder.getOrderDate());
        assertEquals(newOrder.getTotalPrice(), existingOrder.getTotalPrice());

        verify(orderRepository, times(1)).findById(orderId);
        verify(orderRepository, times(1)).save(existingOrder);

        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        assertThrows(ExceptionsConfig.ResourceNotFoundException.class, () -> orderService.getById(orderId));

        verify(orderRepository, times(2)).findById(orderId);
    }

    @DisplayName("JUnit test for deleteById method")
    @Test
    public void testDeleteById(){
        Integer orderId = 1;

        Order order = new Order();

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

        boolean result = orderService.deleteById(orderId);
        assertTrue(result);

        verify(orderRepository, times(1)).findById(orderId);
        verify(orderRepository, times(1)).delete(order);

        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        assertThrows(ExceptionsConfig.ResourceNotFoundException.class, () -> orderService.deleteById(orderId));

        verify(orderRepository, times(2)).findById(orderId);
    }

}
