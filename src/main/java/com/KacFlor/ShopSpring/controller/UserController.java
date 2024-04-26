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

    @PreAuthorize("hasAuthority('" + Role.Fields.ADMIN + "')")
    @DeleteMapping("/{id}")
    public boolean deleteById(@PathVariable("id") Integer id){
        return userService.deleteUserById(id);
    }

    @PreAuthorize("hasAuthority('" + Role.Fields.ADMIN + "')")
    @GetMapping("/users")
    public List<User> getUsersList(){
        return userService.getAllUsers();
    }


    @PreAuthorize("hasAnyAuthority('" + Role.Fields.USER + "', '" + Role.Fields.ADMIN + "')")
    @GetMapping("/me")
    public User getUser(){
        return userService.getCurrentUser();
    }

    @PreAuthorize("hasAnyAuthority('" + Role.Fields.USER + "', '" + Role.Fields.ADMIN + "')")
    @PatchMapping("/me")
    public boolean loginChange(@RequestBody String newLogin){
        return userService.changeName(newLogin);
    }

    @PreAuthorize("hasAuthority('" + Role.Fields.USER + "')")
    @DeleteMapping("/me")
    public boolean deleteUser(){
        return userService.deleteUser();
    }

}
