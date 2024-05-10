package com.KacFlor.ShopSpring.integrationTests;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.KacFlor.ShopSpring.model.Customer;
import com.KacFlor.ShopSpring.model.Role;
import com.KacFlor.ShopSpring.model.User;
import com.KacFlor.ShopSpring.service.JwtService;
import com.KacFlor.ShopSpring.service.UserService;
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
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class UserControllerTest{

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private JwtService jwtService;

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN", "USER"})
    public void testDeleteUserById() throws Exception{

        Integer userId = 1;


        given(userService.deleteUserById(userId)).willReturn(true);


        mockMvc.perform(delete("/user/{id}", userId))
                .andExpect(status().isAccepted());
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN", "USER"})
    public void testGetUsersList() throws Exception{

        List<User> userList = Arrays.asList(
                new User("Test1", "password1", new Customer(), Role.USER),
                new User("Test2", "password2", new Customer(), Role.USER),
                new User("Test3", "password3", new Customer(), Role.USER)
        );
        when(userService.getAllUsers()).thenReturn(userList);

        mockMvc.perform(get("/user/users"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[{'username':'Test1','password':'password1'},{'username':'Test2','password':'password2'},{'username':'Test3','password':'password3'}]"));
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN", "USER"})
    public void testGetUser() throws Exception{
        User user = new User("TestLogin", "passwordLogin", new Customer(), Role.ADMIN);

        when(userService.getCurrentUser()).thenReturn(user);

        mockMvc.perform(get("/user/me"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"username\":\"TestLogin\",\"password\":\"passwordLogin\"}"));
    }


    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN", "USER"})
    public void testLoginChange() throws Exception{
        User user = new User("TestLogin", "passwordLogin", new Customer(), Role.ADMIN);

        String newLogin = "1resu";
        when(userService.changeName(newLogin)).thenReturn(true);

        mockMvc.perform(patch("/user/me").content(newLogin).contentType(MediaType.TEXT_PLAIN))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN", "USER"})
    public void testDeleteUser() throws Exception{

        when(userService.deleteUser()).thenReturn(true);

        mockMvc.perform(delete("/user/me"))
                .andExpect(status().isOk());
    }
}
