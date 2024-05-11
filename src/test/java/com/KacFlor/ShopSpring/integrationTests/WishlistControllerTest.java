package com.KacFlor.ShopSpring.integrationTests;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.KacFlor.ShopSpring.model.Cart;
import com.KacFlor.ShopSpring.model.Wishlist;
import com.KacFlor.ShopSpring.service.CartService;
import com.KacFlor.ShopSpring.service.WishlistService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class WishlistControllerTest{

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WishlistService wishlistService;

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN", "USER"})
    void testGetAll() throws Exception{
        Wishlist wishlist1 = new Wishlist();
        Wishlist wishlist2 = new Wishlist();
        Wishlist wishlist3 = new Wishlist();

        when(wishlistService.getAll()).thenReturn(List.of(wishlist1, wishlist2, wishlist3));

        mockMvc.perform(get("/wishlists"))
                .andExpect(status().isOk());

    }

    @WithMockUser(username = "admin", authorities = {"ADMIN", "USER"})
    @Test
    void testGetById() throws Exception{
        Integer wishlistId = 1;

        Wishlist wishlist1 = new Wishlist();

        wishlist1.setId(wishlistId);

        when(wishlistService.getById(wishlistId)).thenReturn(wishlist1);

        mockMvc.perform(get("/wishlists/{id}", wishlistId))
                .andExpect(status().isOk());

    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN", "USER"})
    void testGetCustomerCart() throws Exception{

        Integer wishlistId = 2;

        Wishlist wishlist1 = new Wishlist();

        when(wishlistService.getCartByCustomerId(wishlistId)).thenReturn(wishlist1);

        mockMvc.perform(get("/wishlists/customer/{id}", wishlistId))
                .andExpect(status().isOk());

    }

}
