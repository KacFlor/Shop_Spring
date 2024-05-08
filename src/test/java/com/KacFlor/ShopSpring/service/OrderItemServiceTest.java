package com.KacFlor.ShopSpring.service;
import com.KacFlor.ShopSpring.config.ExceptionsConfig;
import com.KacFlor.ShopSpring.controllersRequests.NewOrder;
import com.KacFlor.ShopSpring.dao.OrderItemRepository;
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
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderItemServiceTest{

    @Mock
    private OrderItemRepository orderItemRepository;

    @InjectMocks
    private OrderItemService orderItemService;

    @DisplayName("JUnit test for getAll method")
    @Test
    public void testGetAll()
    {
        OrderItem orderItem1 = new OrderItem("Test1", 222.0, 22.22);
        OrderItem orderItem2 = new OrderItem("Test2", 333.0, 22.22);
        OrderItem orderItem3 = new OrderItem("Test3", 333.0, 22.22);

        given(orderItemRepository.findAll()).willReturn(List.of(orderItem1, orderItem2, orderItem3));

        List<OrderItem> result = orderItemService.getAll();

        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(3);

        assertThat(result.get(0).getName()).isEqualTo("Test1");
        assertThat(result.get(1).getName()).isEqualTo("Test2");
        assertThat(result.get(2).getName()).isEqualTo("Test3");
    }

    @DisplayName("JUnit test for getById method")
    @Test
    public void testGetById(){

        OrderItem orderItem1 = new OrderItem("Test1", 222.0, 22.22);
        Integer itemId = 1;

        when(orderItemRepository.findById(itemId)).thenReturn(Optional.of(orderItem1));

        OrderItem orderItem = orderItemService.getOrderItemById(itemId);

        assertEquals(orderItem1, orderItem);

        verify(orderItemRepository, times(1)).findById(itemId);

        when(orderItemRepository.findById(itemId)).thenReturn(Optional.empty());

        assertThrows(ExceptionsConfig.ResourceNotFoundException.class, () -> orderItemService.getOrderItemById(itemId));

        verify(orderItemRepository, times(2)).findById(itemId);

    }

    @DisplayName("JUnit test for deleteById method")
    @Test
    public void testDeleteById(){
        Integer itemId = 1;

        OrderItem orderItem1 = new OrderItem("Test1", 222.0, 22.22);

        when(orderItemRepository.findById(itemId)).thenReturn(Optional.of(orderItem1));

        boolean result = orderItemService.deleteById(itemId);
        assertTrue(result);

        verify(orderItemRepository, times(1)).findById(itemId);
        verify(orderItemRepository, times(1)).delete(orderItem1);

        when(orderItemRepository.findById(itemId)).thenReturn(Optional.empty());

        assertThrows(ExceptionsConfig.ResourceNotFoundException.class, () -> orderItemService.deleteById(itemId));

        verify(orderItemRepository, times(2)).findById(itemId);
    }

}
