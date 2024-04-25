package com.KacFlor.ShopSpring.service;

import com.KacFlor.ShopSpring.model.Category;
import com.KacFlor.ShopSpring.dao.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService{

    final private CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getCategory(){
        return categoryRepository.findAll();
    }
}
