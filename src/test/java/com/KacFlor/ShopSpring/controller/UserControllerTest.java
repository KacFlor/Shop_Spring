package com.KacFlor.ShopSpring.controller;

import com.KacFlor.ShopSpring.model.Customer;
import com.KacFlor.ShopSpring.model.Role;
import com.KacFlor.ShopSpring.model.User;
import com.KacFlor.ShopSpring.service.UserService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.testng.Assert.assertEquals;

public class UserControllerTest{

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeClass
    public void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testDeleteUserById(){

        Integer userId = 1;
        given(userService.deleteUserById(userId)).willReturn(true);

        boolean result = userController.deleteById(userId);

        assertEquals(result, true);
    }

    @Test
    public void testGetUsersList(){

        List<User> userList = Arrays.asList(
                new User("Test1", "password1", new Customer(), Role.USER),
                new User("Test2", "password2", new Customer(), Role.USER),
                new User("Test3", "password3", new Customer(), Role.USER)
        );
        given(userService.getAllUsers()).willReturn(userList);

        List<User> result = userController.getUsersList();

        assertEquals(result.size(), 3);
    }

    @Test
    public void testGetUser(){

        User user = new User("Test1", "password", new Customer(), Role.USER);
        given(userService.getCurrentUser()).willReturn(user);

        User result = userController.getUser();

        assertEquals(result, user);
    }

    @Test
    public void testLoginChange(){

        String newLogin = "1resu";
        given(userService.changeName(newLogin)).willReturn(true);

        boolean result = userController.loginChange(newLogin);

        assertEquals(result, true);
    }

    @Test
    public void testDeleteUser(){

        given(userService.deleteUser()).willReturn(true);

        boolean result = userController.deleteUser();

        assertEquals(result, true);
    }
}
