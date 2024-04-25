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

    public User getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails) principal).getUsername();

        return userRepository.findByLogin(username);
    }


    public Boolean changeName(String newName){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails) principal).getUsername();


        User user = userRepository.findByLogin(username);
        user.setLogin(newName);

        userRepository.save(user);

        return Boolean.TRUE;
    }

    public Boolean deleteUserByLogin(String Login){

        User user = userRepository.findByLogin(Login);

        if (user != null) {
            userRepository.delete(user);
            return Boolean.TRUE;
        }
        else{
            throw new UsernameNotFoundException("User not found");
        }
    }

    public Boolean deleteUserById(Integer userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            userRepository.delete(user);
            return Boolean.TRUE;
        } else {
            throw new UsernameNotFoundException("User with id '" + userId + "' not found");
        }
    }

    public Boolean deleteUser(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails) principal).getUsername();

        User user = userRepository.findByLogin(username);

        if (user != null && username.equals(user.getLogin())) {
            userRepository.delete(user);
            return Boolean.TRUE;
        }
        else{
            throw new UsernameNotFoundException("User with login '" + username + "' not found");
        }
    }

}
