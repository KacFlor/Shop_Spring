package com.example.shopSpring.service;

import com.example.shopSpring.model.Category;
import com.example.shopSpring.dao.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    final private CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getCategory() {
        return categoryRepository.findAll();
    }
}
