//package com.KacFlor.ShopSpring.controller;
//
//import static org.mockito.Mockito.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//import com.KacFlor.ShopSpring.controllersRequests.NewSupplier;
//import com.KacFlor.ShopSpring.model.Supplier;
//import com.KacFlor.ShopSpring.service.SupplierService;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.node.ObjectNode;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.util.List;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//@ExtendWith(MockitoExtension.class)
//public class SupplierControllerTest{
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private SupplierService supplierService;
//
//    @Test
//    @WithMockUser(username = "admin", authorities = {"ADMIN", "USER"})
//    void testGetAll() throws Exception{
//        Supplier supplier1 = new Supplier("Test1");
//        Supplier supplier2 = new Supplier("Test2");
//        Supplier supplier3 = new Supplier("Test3");
//
//        when(supplierService.getAll()).thenReturn(List.of(supplier1, supplier2, supplier3));
//
//        mockMvc.perform(get("/suppliers"))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(content().json("[{'name':'Test1'}, {'name':'Test2'}, {'name':'Test3'}]"));
//    }
//
//    @WithMockUser(username = "admin", authorities = {"ADMIN", "USER"})
//    @Test
//    void testGetById() throws Exception{
//        Integer supplierId = 1;
//
//        Supplier supplier1 = new Supplier("Test1");
//
//        supplier1.setId(supplierId);
//
//        when(supplierService.getById(supplierId)).thenReturn(supplier1);
//
//        mockMvc.perform(get("/suppliers/{id}", supplierId))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(content().json("{'name':'Test1'}"));
//
//    }
//
//    @Test
//    @WithMockUser(username = "admin", authorities = {"ADMIN", "USER"})
//    void testDeleteById() throws Exception{
//        Integer supplierId = 2;
//        when(supplierService.deleteById(supplierId)).thenReturn(true);
//
//        mockMvc.perform(delete("/suppliers/{id}", supplierId))
//                .andExpect(status().isOk());
//    }
//
//    @WithMockUser(username = "admin", authorities = {"ADMIN"})
//    @Test
//    void testUpdateSupplier() throws Exception{
//
//        NewSupplier newSupplier = new NewSupplier("Summer");
//        Integer supplierId = 1;
//
//        when(supplierService.updateSupplier(newSupplier, supplierId)).thenReturn(true);
//
//        ObjectMapper objectMapper = new ObjectMapper();
//        ObjectNode requestJsonNode = objectMapper.createObjectNode();
//
//        requestJsonNode.put("name", newSupplier.getName());
//
//        String requestJson = objectMapper.writeValueAsString(requestJsonNode);
//
//        mockMvc.perform(patch("/suppliers/{id}", supplierId).content(requestJson).contentType(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().isOk());
//
//    }
//
//    @WithMockUser(username = "admin", authorities = {"ADMIN"})
//    @Test
//    void testCreateNewSupplier() throws Exception{
//
//        NewSupplier newSupplier = new NewSupplier("Summer");
//
//        when(supplierService.addNewSupplier(newSupplier)).thenReturn(true);
//
//        ObjectMapper objectMapper = new ObjectMapper();
//        ObjectNode requestJsonNode = objectMapper.createObjectNode();
//
//        requestJsonNode.put("name", newSupplier.getName());
//
//        String requestJson = objectMapper.writeValueAsString(requestJsonNode);
//
//        mockMvc.perform(post("/suppliers/new").content(requestJson).contentType(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().isAccepted());
//
//    }
//}
