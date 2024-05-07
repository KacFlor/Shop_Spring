package com.KacFlor.ShopSpring.service;


import com.KacFlor.ShopSpring.config.ExceptionsConfig;
import com.KacFlor.ShopSpring.controllersRequests.NewOrderItem;
import com.KacFlor.ShopSpring.controllersRequests.NewProduct;
import com.KacFlor.ShopSpring.dao.*;
import com.KacFlor.ShopSpring.model.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
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

    @DisplayName("JUnit test for getAll method")
    @Test
    public void testGetAll(){
        Product product1 = new Product("Test1", "Old Name", "Old description", 29.99, 100);
        Product product2 = new Product("Test2", "Old Name", "Old description", 29.99, 100);
        Product product3 = new Product("Test3", "Old Name", "Old description", 29.99, 100);

        given(productRepository.findAll()).willReturn(List.of(product1, product2, product3));

        List<Product> result = productService.getAll();

        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(3);

        assertThat(result.get(0).getSku()).isEqualTo("Test1");
        assertThat(result.get(1).getSku()).isEqualTo("Test2");
        assertThat(result.get(2).getSku()).isEqualTo("Test3");
    }

    @DisplayName("JUnit test for getById method")
    @Test
    public void testGetById(){

        Product product1 = new Product("Test1", "Old Name", "Old description", 29.99, 100);
        Integer productId = 1;

        when(productRepository.findById(productId)).thenReturn(Optional.of(product1));

        Product product = productService.getById(productId);

        assertEquals(product, product1);

        verify(productRepository, times(1)).findById(productId);

        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        assertThrows(ExceptionsConfig.ResourceNotFoundException.class, () -> productService.getById(productId));

        verify(productRepository, times(2)).findById(productId);

    }

    @DisplayName("JUnit test for deleteById method")
    @Test
    public void testDeleteById(){
        Integer productId = 1;

        Product product1 = new Product("Test1", "Old Name", "Old description", 29.99, 100);

        when(productRepository.findById(productId)).thenReturn(Optional.of(product1));

        boolean result = productService.deleteById(productId);
        assertTrue(result);

        verify(productRepository, times(1)).findById(productId);
        verify(productRepository, times(1)).delete(product1);

        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        assertThrows(ExceptionsConfig.ResourceNotFoundException.class, () -> productService.deleteById(productId));

        verify(productRepository, times(2)).findById(productId);
    }

    @DisplayName("JUnit test for updateProduct method")
    @Test
    public void testUpdateProduct(){

        Integer productId = 1;
        NewProduct newProduct = new NewProduct("Test1", "Updated Product", "Updated description", 39.99, 50);

        Product existingProduct = new Product("OldSKU", "Old Name", "Old description", 29.99, 100);
        existingProduct.setId(productId);

        when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));

        boolean result = productService.updateProduct(newProduct, productId);

        assertTrue(result);
        assertEquals(existingProduct.getSku(), newProduct.getSku());
        assertEquals(existingProduct.getName(), newProduct.getName());
        assertEquals(existingProduct.getDescription(), newProduct.getDescription());
        assertEquals(existingProduct.getPrice(), newProduct.getPrice());
        assertEquals(existingProduct.getStock(), newProduct.getStock());

        verify(productRepository, times(1)).findById(productId);
        verify(productRepository, times(1)).save(existingProduct);

        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        assertThrows(ExceptionsConfig.ResourceNotFoundException.class, () -> productService.updateProduct(newProduct, productId));

        verify(productRepository, times(2)).findById(productId);
    }

    @DisplayName("JUnit test for addNewProduct method")
    @Test
    public void testAddNewProduct(){

        NewProduct newProduct = new NewProduct("Test1", "New Product", "Description for new product", 19.99, 20);

        boolean result = productService.addNewProduct(newProduct);
        assertTrue(result);

        verify(productRepository, times(1)).save(any(Product.class));
    }


}