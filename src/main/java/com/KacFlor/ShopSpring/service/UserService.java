package com.KacFlor.ShopSpring.service;

import com.KacFlor.ShopSpring.dao.UserRepository;
import com.KacFlor.ShopSpring.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getUser() {
        return userRepository.findAll();
    }
}
