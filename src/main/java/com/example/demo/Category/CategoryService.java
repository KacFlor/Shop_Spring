package com.example.demo.Category;

import com.example.demo.Cart.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    public CategoryService() {

    }

    public List<Category> getCategory() {return List;}
}
