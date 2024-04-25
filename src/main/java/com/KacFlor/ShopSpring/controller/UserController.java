package com.KacFlor.ShopSpring.controller;


import java.util.List;

import com.KacFlor.ShopSpring.model.Role;
import com.KacFlor.ShopSpring.model.User;
import com.KacFlor.ShopSpring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(
        path = {"user"}
)
public class UserController{

    private final UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @PreAuthorize("hasAuthority('" + Role.Fields.USER + "')")
    @GetMapping("/user-name")
    public String getName(){
        return userService.getName();
    }

    @PreAuthorize("hasAuthority('" + Role.Fields.ADMIN + "')")
    @GetMapping("/users-list")
    public List<User> getUsersList(){
        return userService.getUser();
    }

    @PreAuthorize("hasAnyAuthority('" + Role.Fields.USER + "', '" + Role.Fields.ADMIN + "')")
    @PatchMapping("/login-change")
    public String loginChange(@RequestBody String newLogin){
        return userService.changeName(newLogin);
    }

    @PreAuthorize("hasAuthority('" + Role.Fields.USER + "')")
    @DeleteMapping("/delete-user-account")
    public String deleteUser(){
        return userService.deleteUser();
    }

    @PreAuthorize("hasAuthority('" + Role.Fields.ADMIN + "')")
    @DeleteMapping("/delete-user-account-for-adm")
    public String deleteUserForAdm(@RequestBody String login){
        return userService.deleteUserForAdm(login);
    }
}
