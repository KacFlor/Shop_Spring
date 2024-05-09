package com.KacFlor.ShopSpring.service;

import com.KacFlor.ShopSpring.config.ExceptionsConfig;
import com.KacFlor.ShopSpring.controllersRequests.NewCategory;
import com.KacFlor.ShopSpring.model.Category;
import com.KacFlor.ShopSpring.dao.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService{

    final private CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getAll(){
        return categoryRepository.findAll();
    }

    public Category getById(Integer Id){

        Optional<Category> optionalCategory = categoryRepository.findById(Id);

        if (optionalCategory.isEmpty()) {
            throw new ExceptionsConfig.ResourceNotFoundException("Category not found");
        }

        return optionalCategory.get();

    }

    public boolean deleteById(Integer Id){

        Optional<Category> optionalCategory = categoryRepository.findById(Id);

        if (optionalCategory.isEmpty()) {
            throw new ExceptionsConfig.ResourceNotFoundException("Category not found");
        }

        Category category = optionalCategory.get();

        categoryRepository.delete(category);
        return true;

    }

    public boolean updateCategory(NewCategory newCategory, Integer Id){
        Optional<Category> optionalCategory = categoryRepository.findById(Id);

        if (optionalCategory.isEmpty()) {
            throw new ExceptionsConfig.ResourceNotFoundException("Category not found");
        }

        Category category = optionalCategory.get();
        category.setName(newCategory.getName());

        categoryRepository.save(category);

        return true;

    }

    public boolean addNewCategory(NewCategory newCategory){

        Category category = new Category();

        category.setName(newCategory.getName());

        categoryRepository.save(category);

        return true;

    }
}
