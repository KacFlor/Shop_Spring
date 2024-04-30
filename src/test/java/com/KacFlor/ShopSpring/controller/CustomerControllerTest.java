package com.KacFlor.ShopSpring.controller;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.KacFlor.ShopSpring.controllersRequests.CustomerUpdateRequest;
import com.KacFlor.ShopSpring.model.Customer;
import com.KacFlor.ShopSpring.model.Role;
import com.KacFlor.ShopSpring.model.User;
import com.KacFlor.ShopSpring.service.CustomerService;
import com.KacFlor.ShopSpring.service.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class CustomerControllerTest{

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private JwtService jwtService;

    private User user1;

    private User user2;

    private User hAdmin;

    private Customer customer = new Customer();

    private Customer customer1 = new Customer();

    private Customer customer2 = new Customer();

    @BeforeEach
    public void setup(){
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        user1 = new User("Test1", "encodedPassword1", customer1, Role.USER);
        user2 = new User("Test2", "encodedPassword2", customer2, Role.USER);
        hAdmin = new User("HAdmin", encoder.encode("1234"), customer, Role.ADMIN);
        customer1.setUser(user1);
        customer2.setUser(user2);
        customer.setUser(hAdmin);
        customer1.setFirstName("Test1");
        customer2.setFirstName("Test2");
        customer.setFirstName("HAdmin");
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN", "USER"})
    public void testDeleteById() throws Exception{

        Integer userId = 1;

        given(customerService.deleteCustomerById(userId)).willReturn(true);


        mockMvc.perform(delete("/customer/{id}", userId))
                .andExpect(status().isAccepted());

    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN", "USER"})
    public void testGetCustomersList() throws Exception{

        when(customerService.getAll()).thenReturn(List.of(customer1, customer2, customer));

        mockMvc.perform(get("/customer/all"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[{'firstName':'Test1'},{'firstName':'Test2'},{'firstName':'HAdmin'}]"));


    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN", "USER"})
    public void testGetCustomer() throws Exception{

        when(customerService.getCurrent()).thenReturn(customer1);

        mockMvc.perform(get("/customer/me"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{'firstName':'Test1'}"));

    }


    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN", "USER"})
    public void testDataChange() throws Exception{

        CustomerUpdateRequest customerUpdateRequest = new CustomerUpdateRequest();
        customerUpdateRequest.setFirstName("Kacper");
        customerUpdateRequest.setLastName("Florczyk");
        customerUpdateRequest.setEmail("cos@gmail.com");
        customerUpdateRequest.setAddress("Staffa");
        customerUpdateRequest.setPhoneNumber(123123123L);

        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(customerUpdateRequest);

        when(customerService.dataChange(customerUpdateRequest)).thenReturn(true);

        mockMvc.perform(patch("/customer/me")
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN", "USER"})
    public void testDeleteCustomer() throws Exception{

        when(customerService.deleteCustomer()).thenReturn(true);

        mockMvc.perform(delete("/customer/me"))
                .andExpect(status().isOk());

    }

}
