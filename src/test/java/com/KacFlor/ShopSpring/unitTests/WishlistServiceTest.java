package com.KacFlor.ShopSpring.unitTests;
import com.KacFlor.ShopSpring.config.ExceptionsConfig;
import com.KacFlor.ShopSpring.dao.*;
import com.KacFlor.ShopSpring.model.*;
import com.KacFlor.ShopSpring.service.CartService;
import com.KacFlor.ShopSpring.service.WishlistService;
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
public class WishlistServiceTest{

    @Mock
    private WishlistRepository wishlistRepository;

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private WishlistService wishlistService;


    @DisplayName("JUnit test for getAll method")
    @Test
    public void testGetAll(){
        Wishlist wishlist1 = new Wishlist();
        Wishlist wishlist2 = new Wishlist();
        Wishlist wishlist3 = new Wishlist();

        given(wishlistRepository.findAll()).willReturn(List.of(wishlist1, wishlist2, wishlist3));

        List<Wishlist> result = wishlistService.getAll();
        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(3);

    }

    @DisplayName("JUnit test for getById method")
    @Test
    public void testGetById(){

        Wishlist wishlist1 = new Wishlist();
        Integer wishlistId = 1;

        when(wishlistRepository.findById(wishlistId)).thenReturn(Optional.of(wishlist1));

        Wishlist wishlist = wishlistService.getById(wishlistId);

        assertEquals(wishlist, wishlist1);

        verify(wishlistRepository, times(1)).findById(wishlistId);

        when(wishlistRepository.findById(wishlistId)).thenReturn(Optional.empty());

        assertThrows(ExceptionsConfig.ResourceNotFoundException.class, () -> wishlistService.getById(wishlistId));

        verify(wishlistRepository, times(2)).findById(wishlistId);

    }

    @DisplayName("JUnit test for testGetAllCustomerId method")
    @Test
    public void testGetByCustomerId(){

        Integer customer1Id = 1;
        Customer customer = new Customer();

        Wishlist wishlist1 = new Wishlist();

        wishlist1.setCustomer(customer);
        customer.setWishlist(wishlist1);

        when(customerRepository.findById(customer1Id)).thenReturn(Optional.of(customer));
        when(wishlistRepository.findByCustomerId(customer1Id)).thenReturn(wishlist1);

        Wishlist result = wishlistService.getCartByCustomerId(customer1Id);

        verify(customerRepository, times(1)).findById(customer1Id);
        verify(wishlistRepository, times(1)).findByCustomerId(customer1Id);

        when(customerRepository.findById(customer1Id)).thenReturn(Optional.empty());

        assertThrows(ExceptionsConfig.ResourceNotFoundException.class, () -> wishlistService.getCartByCustomerId(customer1Id));

        verify(customerRepository, times(2)).findById(customer1Id);

    }

}
