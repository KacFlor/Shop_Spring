package com.KacFlor.ShopSpring.service;
import com.KacFlor.ShopSpring.config.ExceptionsConfig;
import com.KacFlor.ShopSpring.controllersRequests.NewCategory;
import com.KacFlor.ShopSpring.controllersRequests.NewSupplier;
import com.KacFlor.ShopSpring.dao.*;
import com.KacFlor.ShopSpring.model.*;
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
public class SupplierServiceTest{
    @Mock
    private SupplierRepository supplierRepository;

    @InjectMocks
    private SupplierService supplierService;

    @DisplayName("JUnit test for getAll method")
    @Test
    public void testGetAll(){
        Supplier supplier1 = new Supplier("Test1");
        Supplier supplier2 = new Supplier("Test2");
        Supplier supplier3 = new Supplier("Test3");

        given(supplierRepository.findAll()).willReturn(List.of(supplier1, supplier2, supplier3));

        List<Supplier> result = supplierService.getAll();
        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(3);

        assertThat(result.get(0).getName()).isEqualTo("Test1");
        assertThat(result.get(1).getName()).isEqualTo("Test2");
        assertThat(result.get(2).getName()).isEqualTo("Test3");
    }

    @DisplayName("JUnit test for getById method")
    @Test
    public void testGetById(){

        Supplier supplier1 = new Supplier("Test1");
        Integer supplierId = 1;

        when(supplierRepository.findById(supplierId)).thenReturn(Optional.of(supplier1));

        Supplier supplier = supplierService.getById(supplierId);

        assertEquals(supplier, supplier1);

        verify(supplierRepository, times(1)).findById(supplierId);

        when(supplierRepository.findById(supplierId)).thenReturn(Optional.empty());

        assertThrows(ExceptionsConfig.ResourceNotFoundException.class, () -> supplierService.getById(supplierId));

        verify(supplierRepository, times(2)).findById(supplierId);

    }

    @DisplayName("JUnit test for deleteById method")
    @Test
    public void testDeleteById(){
        Integer supplierId = 1;

        Supplier supplier1 = new Supplier("Test1");

        when(supplierRepository.findById(supplierId)).thenReturn(Optional.of(supplier1));

        boolean result = supplierService.deleteById(supplierId);
        assertTrue(result);

        verify(supplierRepository, times(1)).findById(supplierId);
        verify(supplierRepository, times(1)).delete(supplier1);

        when(supplierRepository.findById(supplierId)).thenReturn(Optional.empty());

        assertThrows(ExceptionsConfig.ResourceNotFoundException.class, () -> supplierService.deleteById(supplierId));

        verify(supplierRepository, times(2)).findById(supplierId);
    }

    @DisplayName("JUnit test for updateProduct method")
    @Test
    public void testUpdateCategory(){

        Integer supllierId = 1;
        NewSupplier newSupplier = new NewSupplier("Summer");

        Supplier existingSupplier = new Supplier("Test1");
        existingSupplier.setId(supllierId);

        when(supplierRepository.findById(supllierId)).thenReturn(Optional.of(existingSupplier));

        boolean result = supplierService.updateSupplier(newSupplier, supllierId);

        assertTrue(result);
        assertEquals(existingSupplier.getName(), newSupplier.getName());

        verify(supplierRepository, times(1)).findById(supllierId);
        verify(supplierRepository, times(1)).save(existingSupplier);

        when(supplierRepository.findById(supllierId)).thenReturn(Optional.empty());

        assertThrows(ExceptionsConfig.ResourceNotFoundException.class, () -> supplierService.updateSupplier(newSupplier, supllierId));

        verify(supplierRepository, times(2)).findById(supllierId);
    }

    @DisplayName("JUnit test for addNewCategory method")
    @Test
    public void testAddNewCategory(){

        NewSupplier newSupplier = new NewSupplier("Summer");

        boolean result = supplierService.addNewSupplier(newSupplier);
        assertTrue(result);

        verify(supplierRepository, times(1)).save(any(Supplier.class));
    }
}
