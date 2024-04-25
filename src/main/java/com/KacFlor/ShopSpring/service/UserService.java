package com.KacFlor.ShopSpring.service;

import com.KacFlor.ShopSpring.dao.UserRepository;
import com.KacFlor.ShopSpring.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService{

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public List<User> getUser(){
        return userRepository.findAll();
    }

    public String changeName(String newName){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails) principal).getUsername();

        User user = userRepository.findByLogin(username);
        user.setLogin(newName);

        userRepository.save(user);

        return "Login Changed";
    }

    public String deleteUserForAdm(String Login){

        User user = userRepository.findByLogin(Login);

        if (user != null) {
            userRepository.delete(user);
            return "User deleted";
        }
        else{
            return "ERROR";
        }
    }

    public String deleteUser(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails) principal).getUsername();

        User user = userRepository.findByLogin(username);

        if (user != null && username.equals(user.getLogin())) {
            userRepository.delete(user);
            return "User deleted";
        }
        else{
            return "ERROR";
        }
    }

    public String getName(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails) principal).getUsername();
        return username;
    }

}
