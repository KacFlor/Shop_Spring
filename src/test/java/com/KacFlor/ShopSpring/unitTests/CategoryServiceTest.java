package com.KacFlor.ShopSpring.unitTests;

import com.KacFlor.ShopSpring.Exceptions.ExceptionsConfig;
import com.KacFlor.ShopSpring.controllersRequests.NewCategory;
import com.KacFlor.ShopSpring.dao.*;
import com.KacFlor.ShopSpring.model.*;
import com.KacFlor.ShopSpring.service.CategoryService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest{

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    @DisplayName("JUnit test for getAll method")
    @Test
    public void testGetAll(){
        Category category1 = new Category("Test1");
        Category category2 = new Category("Test2");
        Category category3 = new Category("Test3");

        given(categoryRepository.findAll()).willReturn(List.of(category1, category2, category3));

        List<Category> result = categoryService.getAll();
        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(3);

        assertThat(result.get(0).getName()).isEqualTo("Test1");
        assertThat(result.get(1).getName()).isEqualTo("Test2");
        assertThat(result.get(2).getName()).isEqualTo("Test3");
    }

    @DisplayName("JUnit test for getById method")
    @Test
    public void testGetById(){

        Category category1 = new Category("Test1");
        Integer categoryId = 1;

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category1));

        Category category = categoryService.getById(categoryId);

        assertEquals(category, category1);

        verify(categoryRepository, times(1)).findById(categoryId);

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        assertThrows(ExceptionsConfig.ResourceNotFoundException.class, () -> categoryService.getById(categoryId));

        verify(categoryRepository, times(2)).findById(categoryId);

    }

    @DisplayName("JUnit test for deleteById method")
    @Test
    public void testDeleteById(){
        Integer categoryId = 1;

        Category category1 = new Category("Test1");

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category1));

        boolean result = categoryService.deleteById(categoryId);
        assertTrue(result);

        verify(categoryRepository, times(1)).findById(categoryId);
        verify(categoryRepository, times(1)).delete(category1);

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        assertThrows(ExceptionsConfig.ResourceNotFoundException.class, () -> categoryService.deleteById(categoryId));

        verify(categoryRepository, times(2)).findById(categoryId);
    }

    @DisplayName("JUnit test for updateProduct method")
    @Test
    public void testUpdateCategory(){

        Integer categoryId = 1;
        NewCategory newCategory = new NewCategory("Summer Sale");

        Category existingCategory = new Category("Test1");
        existingCategory.setId(categoryId);

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(existingCategory));

        boolean result = categoryService.updateCategory(newCategory, categoryId);

        assertTrue(result);
        assertEquals(existingCategory.getName(), newCategory.getName());

        verify(categoryRepository, times(1)).findById(categoryId);
        verify(categoryRepository, times(1)).save(existingCategory);

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        assertThrows(ExceptionsConfig.ResourceNotFoundException.class, () -> categoryService.updateCategory(newCategory, categoryId));

        verify(categoryRepository, times(2)).findById(categoryId);
    }

    @DisplayName("JUnit test for addNewCategory method")
    @Test
    public void testAddNewCategory(){

        NewCategory newCategory = new NewCategory("Summer Sale");

        boolean result = categoryService.addNewCategory(newCategory);
        assertTrue(result);

        verify(categoryRepository, times(1)).save(any(Category.class));
    }
}
