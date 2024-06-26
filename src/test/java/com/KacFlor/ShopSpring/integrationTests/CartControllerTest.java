package com.KacFlor.ShopSpring.integrationTests;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.KacFlor.ShopSpring.model.Cart;
import com.KacFlor.ShopSpring.service.CartService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
@DirtiesContext
public class CartControllerTest{

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CartService cartService;

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN", "USER"})
    void testGetAll() throws Exception{
        Cart cart1 = new Cart(1.00);
        Cart cart2 = new Cart(2.00);
        Cart cart3 = new Cart(3.00);

        when(cartService.getAll()).thenReturn(List.of(cart1, cart2, cart3));

        mockMvc.perform(get("/carts"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[{'quantity': 1.00 }, {'quantity': 2.00 }, {'quantity': 3.00 }]"));
    }

    @WithMockUser(username = "admin", authorities = {"ADMIN", "USER"})
    @Test
    void testGetById() throws Exception{
        Integer cartId = 1;

        Cart cart1 = new Cart(1.00);

        cart1.setId(cartId);

        when(cartService.getById(cartId)).thenReturn(cart1);

        mockMvc.perform(get("/carts/{id}", cartId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{'quantity': 1 }"));

    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN", "USER"})
    void testGetCustomerCart() throws Exception{

        Integer customerId = 2;

        Cart cart1 = new Cart(1.00);

        when(cartService.getCartByCustomerId(customerId)).thenReturn(cart1);

        mockMvc.perform(get("/carts/customer").param("Cid", String.valueOf(customerId)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{'quantity': 1 }"));

    }
}
