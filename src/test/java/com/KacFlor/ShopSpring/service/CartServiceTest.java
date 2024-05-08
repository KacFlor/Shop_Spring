package com.KacFlor.ShopSpring.service;

import com.KacFlor.ShopSpring.config.ExceptionsConfig;
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

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class CartServiceTest{


    @Mock
    private CartRepository cartRepository;

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CartService cartService;


    @DisplayName("JUnit test for getAll method")
    @Test
    public void testGetAll(){
        Cart cart1 = new Cart(1);
        Cart cart2 = new Cart(2);
        Cart cart3 = new Cart(3);

        given(cartRepository.findAll()).willReturn(List.of(cart1, cart2, cart3));

        List<Cart> result = cartService.getAll();
        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(3);

        assertThat(result.get(0).getQuantity()).isEqualTo(1);
        assertThat(result.get(1).getQuantity()).isEqualTo(2);
        assertThat(result.get(2).getQuantity()).isEqualTo(3);
    }

    @DisplayName("JUnit test for getById method")
    @Test
    public void testGetById(){

        Cart cart1 = new Cart(1);
        Integer cartId = 1;

        when(cartRepository.findById(cartId)).thenReturn(Optional.of(cart1));

        Cart cart = cartService.getById(cartId);

        assertEquals(cart, cart1);

        verify(cartRepository, times(1)).findById(cartId);

        when(cartRepository.findById(cartId)).thenReturn(Optional.empty());

        assertThrows(ExceptionsConfig.ResourceNotFoundException.class, () -> cartService.getById(cartId));

        verify(cartRepository, times(2)).findById(cartId);

    }

    @DisplayName("JUnit test for testGetAllCustomerId method")
    @Test
    public void testGetByCustomerId(){

        Integer customer1Id = 1;
        Customer customer = new Customer();

        Cart cart1 = new Cart(1);

        cart1.setCustomer(customer);
        customer.setCart(cart1);

        when(customerRepository.findById(customer1Id)).thenReturn(Optional.of(customer));
        when(cartRepository.findByCustomerId(customer1Id)).thenReturn(cart1);

        Cart result = cartService.getCartByCustomerId(customer1Id);

        verify(customerRepository, times(1)).findById(customer1Id);
        verify(cartRepository, times(1)).findByCustomerId(customer1Id);

        when(customerRepository.findById(customer1Id)).thenReturn(Optional.empty());

        assertThrows(ExceptionsConfig.ResourceNotFoundException.class, () -> cartService.getCartByCustomerId(customer1Id));

        verify(customerRepository, times(2)).findById(customer1Id);

    }

}
