package com.KacFlor.ShopSpring.service;

import com.KacFlor.ShopSpring.dao.UserRepository;
import com.KacFlor.ShopSpring.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService{

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public List<User> getAllUser(){
        return userRepository.findAll();
    }

    public User getCurrentUser(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails) principal).getUsername();

        return userRepository.findByLogin(username);
    }


    public boolean changeName(String newName){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails) principal).getUsername();


        User user = userRepository.findByLogin(username);
        user.setLogin(newName);

        userRepository.save(user);

        return true;
    }

    public boolean deleteUserById(Integer userId){
        Optional<User> userOptional = userRepository.findById(userId);
        if(userOptional.isPresent()){
            User user = userOptional.get();
            userRepository.delete(user);
            return true;
        }else{
            throw new UsernameNotFoundException("User not found");
        }
    }

    public boolean deleteUser(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails) principal).getUsername();

        User user = userRepository.findByLogin(username);

        if(user != null && username.equals(user.getLogin())){
            userRepository.delete(user);
            return true;
        }else{
            throw new UsernameNotFoundException("User not found");
        }
    }

}
