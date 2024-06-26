package com.KacFlor.ShopSpring.integrationTests;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.KacFlor.ShopSpring.controllersRequests.NewCardData;
import com.KacFlor.ShopSpring.controllersRequests.NewCustomer;
import com.KacFlor.ShopSpring.controllersRequests.NewShipment;
import com.KacFlor.ShopSpring.model.Customer;
import com.KacFlor.ShopSpring.service.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
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

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
@DirtiesContext
public class CustomerControllerTest{

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN", "USER"})
    void testCreateShipment() throws Exception{

        NewShipment newShipment = new NewShipment();
        newShipment.setShipmentDate(LocalDate.of(2024, 5, 1));
        newShipment.setAddress("Test Address");
        newShipment.setCity("Test City");
        newShipment.setState("Test State");
        newShipment.setCountry("Test Country");
        newShipment.setZipcode("12345");

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        String newShipmentJson = objectMapper.writeValueAsString(newShipment);

        when(customerService.createShipment(newShipment)).thenReturn(true);

        mockMvc.perform(post("/customers/me/shipment").contentType(MediaType.APPLICATION_JSON).content(newShipmentJson))
                .andExpect(status().isAccepted());
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN", "USER"})
    void testCreateCardData() throws Exception{

        NewCardData newCardData = new NewCardData();
        newCardData.setCardNum("123-123-123-123");

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        String newCardDataJason = objectMapper.writeValueAsString(newCardData);

        when(customerService.createCardData(newCardData)).thenReturn(true);

        mockMvc.perform(post("/customers/me/card-data").contentType(MediaType.APPLICATION_JSON).content(newCardDataJason))
                .andExpect(status().isAccepted());
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN", "USER"})
    public void testGetById() throws Exception{

        Customer customer1 = new Customer();
        customer1.setFirstName("Test1");
        Integer userId = 1;

        given(customerService.getCustomerById(userId)).willReturn(customer1);

        mockMvc.perform(get("/customers/{id}", userId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{'firstName':'Test1'}"));

    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN", "USER"})
    public void testDeleteById() throws Exception{

        Integer userId = 1;

        given(customerService.deleteCustomerById(userId)).willReturn(true);


        mockMvc.perform(delete("/customers/{id}", userId))
                .andExpect(status().isAccepted());

    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN", "USER"})
    public void testGetCustomersList() throws Exception{
        Customer customer = new Customer();
        Customer customer1 = new Customer();
        Customer customer2 = new Customer();

        customer1.setFirstName("Test1");
        customer2.setFirstName("Test2");
        customer.setFirstName("HAdmin");

        when(customerService.getAll()).thenReturn(List.of(customer1, customer2, customer));

        mockMvc.perform(get("/customers"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[{'firstName':'Test1'},{'firstName':'Test2'},{'firstName':'HAdmin'}]"));

    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN", "USER"})
    public void testGetCustomer() throws Exception{
        Customer customer1 = new Customer();
        customer1.setFirstName("Test1");

        when(customerService.getCurrent()).thenReturn(customer1);

        mockMvc.perform(get("/customers/me"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{'firstName':'Test1'}"));

    }


    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN", "USER"})
    public void testDataChange() throws Exception{

        NewCustomer newCustomer = new NewCustomer();
        newCustomer.setFirstName("Kacper");
        newCustomer.setLastName("Florry");
        newCustomer.setEmail("cos@gmail.com");
        newCustomer.setAddress("Staff");
        newCustomer.setPhoneNumber(123123123L);

        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(newCustomer);

        when(customerService.updateCustomer(newCustomer)).thenReturn(true);

        mockMvc.perform(patch("/customers/me").content(requestJson).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN", "USER"})
    public void testDeleteCustomer() throws Exception{

        when(customerService.deleteCurrentCustomer()).thenReturn(true);

        mockMvc.perform(delete("/customers/me"))
                .andExpect(status().isOk());

    }

}
