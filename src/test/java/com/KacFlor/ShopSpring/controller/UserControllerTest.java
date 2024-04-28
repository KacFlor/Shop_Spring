package com.KacFlor.ShopSpring.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.KacFlor.ShopSpring.model.Customer;
import com.KacFlor.ShopSpring.model.Role;
import com.KacFlor.ShopSpring.model.User;
import com.KacFlor.ShopSpring.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;


import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.BDDMockito.given;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;


    @Test

    public void testDeleteUserById() throws Exception {
        User user = new User("TestLogin", "passwordLogin", new Customer(), Role.ADMIN);

        Authentication auth = new UsernamePasswordAuthenticationToken(user, null,
                Collections.singletonList(new SimpleGrantedAuthority("ADMIN")));

        SecurityContextHolder.getContext().setAuthentication(auth);

        Integer userId = 1;


        given(userService.deleteUserById(userId)).willReturn(true);


        mockMvc.perform(delete("/user/{id}", userId))
                .andExpect(status().isAccepted());
    }

    @Test
    public void testGetUsersList() throws Exception {
        User user = new User("TestLogin", "passwordLogin", new Customer(), Role.ADMIN);

        Authentication auth = new UsernamePasswordAuthenticationToken(user, null,
                Collections.singletonList(new SimpleGrantedAuthority("ADMIN")));

        SecurityContextHolder.getContext().setAuthentication(auth);

        List<User> userList = Arrays.asList(
                new User("Test1", "password1", new Customer(), Role.USER),
                new User("Test2", "password2", new Customer(), Role.USER),
                new User("Test3", "password3", new Customer(), Role.USER)
        );
        when(userService.getAllUsers()).thenReturn(userList);

        mockMvc.perform(get("/user/users"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetUser() throws Exception {
        User user = new User("TestLogin", "passwordLogin", new Customer(), Role.ADMIN);

        Authentication auth = new UsernamePasswordAuthenticationToken(user, null,
                Collections.singletonList(new SimpleGrantedAuthority("USER")));

        SecurityContextHolder.getContext().setAuthentication(auth);

        when(userService.getCurrentUser()).thenReturn(user);

        mockMvc.perform(get("/user/me"))
                .andExpect(status().isOk());
    }

    @Test
    public void testLoginChange() throws Exception {
        User user = new User("TestLogin", "passwordLogin", new Customer(), Role.ADMIN);

        Authentication auth = new UsernamePasswordAuthenticationToken(user, null,
                Collections.singletonList(new SimpleGrantedAuthority("USER")));

        SecurityContextHolder.getContext().setAuthentication(auth);

        String newLogin = "1resu";
        when(userService.changeName(newLogin)).thenReturn(true);

        mockMvc.perform(patch("/user/me")
                        .content(newLogin)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteUser() throws Exception {
        User user = new User("TestLogin", "passwordLogin", new Customer(), Role.ADMIN);

        Authentication auth = new UsernamePasswordAuthenticationToken(user, null,
                Collections.singletonList(new SimpleGrantedAuthority("USER")));

        SecurityContextHolder.getContext().setAuthentication(auth);

        when(userService.deleteUser()).thenReturn(true);

        mockMvc.perform(delete("/user/me"))
                .andExpect(status().isOk());
    }
}
