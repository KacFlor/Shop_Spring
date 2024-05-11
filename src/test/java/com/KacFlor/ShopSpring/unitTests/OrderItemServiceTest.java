package com.KacFlor.ShopSpring.unitTests;
import com.KacFlor.ShopSpring.Exceptions.ExceptionsConfig;
import com.KacFlor.ShopSpring.controllersRequests.NewItem;
import com.KacFlor.ShopSpring.dao.OrderItemRepository;
import com.KacFlor.ShopSpring.dao.OrderRepository;
import com.KacFlor.ShopSpring.dao.ProductRepository;
import com.KacFlor.ShopSpring.model.Order;
import com.KacFlor.ShopSpring.model.OrderItem;
import com.KacFlor.ShopSpring.model.Product;
import com.KacFlor.ShopSpring.service.OrderItemService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.parameters.P;

import java.util.ArrayList;
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

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ProductRepository productRepository;

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
        Integer Oid = 1;
        Integer Pid = 1;

        NewItem newItem = new NewItem();
        newItem.setQuantity(22.00);

        Order order = new Order();
        order.setId(Oid);
        order.setOrderItems(new ArrayList<>());

        Product product = new Product();
        product.setId(Pid);
        product.setOrderItems(new ArrayList<>());

        OrderItem orderItem1 = new OrderItem("Test1", 222.0, 22.22);
        orderItem1.setOrder(order);
        orderItem1.setProduct(product);
        order.getOrderItems().add(orderItem1);
        orderItem1.setName("1");
        product.setName("1");
        order.setTotalPrice(10.00);
        product.setStock(1.00);

        when(orderItemRepository.findById(itemId)).thenReturn(Optional.of(orderItem1));
        when(orderRepository.findById(itemId)).thenReturn(Optional.of(order));
        when(productRepository.findById(itemId)).thenReturn(Optional.of(product));

        boolean result = orderItemService.deleteById(newItem ,Oid, Pid, itemId);
        assertTrue(result);

        verify(orderItemRepository, times(1)).findById(itemId);

        when(orderItemRepository.findById(itemId)).thenReturn(Optional.empty());

        assertThrows(ExceptionsConfig.ResourceNotFoundException.class, () -> orderItemService.deleteById(newItem ,Oid, Pid, itemId));

        verify(orderItemRepository, times(2)).findById(itemId);
    }

}
