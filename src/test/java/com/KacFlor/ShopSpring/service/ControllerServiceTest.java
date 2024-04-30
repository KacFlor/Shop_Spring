package com.KacFlor.ShopSpring.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.KacFlor.ShopSpring.controllersRequests.CustomerUpdateRequest;
import com.KacFlor.ShopSpring.dao.CustomerRepository;
import com.KacFlor.ShopSpring.dao.UserRepository;
import com.KacFlor.ShopSpring.model.Customer;
import com.KacFlor.ShopSpring.model.Role;
import com.KacFlor.ShopSpring.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

import org.springframework.security.core.Authentication;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ControllerServiceTest{

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CustomerService customerService;

    private User user1;

    private User user2;

    private User hAdmin;

    private Customer customer = new Customer();
    private Customer customer1 = new Customer();
    private Customer customer2 = new Customer();
    @BeforeEach
    public void setup(){
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        user1 = new User("Test1", "encodedPassword1", customer1, Role.USER);
        user2 = new User("Test2", "encodedPassword2", customer2, Role.USER);
        hAdmin = new User("HAdmin", encoder.encode("1234"), customer, Role.ADMIN);
        customer1.setUser(user1);
        customer2.setUser(user2);
        customer.setUser(hAdmin);
        customer1.setFirstName("Test1");
        customer2.setFirstName("Test2");
        customer.setFirstName("HAdmin");
    }

    @DisplayName("JUnit test for deleteById method")
    @Test
    public void testDeleteById(){

        Integer CustomerId = 2;

        when(customerRepository.findById(CustomerId)).thenReturn(Optional.ofNullable(customer1));
        when(userRepository.findById(CustomerId)).thenReturn(Optional.ofNullable(user1));

        boolean result = customerService.deleteCustomerById(CustomerId);
        assertTrue(result);

        verify(customerRepository, times(1)).delete(customer1);
        verify(userRepository, times(1)).delete(user1);
    }

    @DisplayName("JUnit test getCustomersList method")
    @Test
    public void testGetCustomersList(){

        given(customerRepository.findAll()).willReturn(List.of(customer1, customer2, customer));

        List<Customer> result = customerService.getAll();

        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(3);
        System.out.println(result);

        assertThat(result.get(0).getFirstName()).isEqualTo("Test1");
        assertThat(result.get(1).getFirstName()).isEqualTo("Test2");
        assertThat(result.get(2).getFirstName()).isEqualTo("HAdmin");
    }

    @DisplayName("JUnit test for getCustomer method")
    @Test
    public void testGetCustomer(){

        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);

        String username = "Test1";
        when(authentication.getName()).thenReturn(username);
        when(securityContext.getAuthentication()).thenReturn(authentication);

        when(userRepository.findByLogin(username)).thenReturn(user1);

        Customer currentCustomer = customerService.getCurrent();

        verify(userRepository, times(1)).findByLogin(username);

        assertThat(currentCustomer).isNotNull();
        assertThat(currentCustomer.getFirstName()).isEqualTo(customer1.getFirstName());

    }

    @DisplayName("JUnit test for dataChange method")
    @Test
    public void testDataChange(){

        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);

        String username = "Test1";
        when(authentication.getName()).thenReturn(username);
        when(securityContext.getAuthentication()).thenReturn(authentication);

        when(userRepository.findByLogin("Test1")).thenReturn(user1);
        when(customerRepository.findByFirstName("Test1")).thenReturn(customer1);

        CustomerUpdateRequest customerUpdateRequest = new CustomerUpdateRequest();
        customerUpdateRequest.setFirstName("Kacper");
        customerUpdateRequest.setLastName("Florczyk");
        customerUpdateRequest.setEmail("cos@gmail.com");
        customerUpdateRequest.setAddress("Staffa");
        customerUpdateRequest.setPhoneNumber(123123123L);

        boolean result = customerService.dataChange(customerUpdateRequest);
        assertTrue(result);

        verify(customerRepository).save(customer1);

        assertThat(customer1.getFirstName()).isNotNull();
        assertThat(customer1.getLastName()).isNotNull();
        assertThat(customer1.getEmail()).isNotNull();
        assertThat(customer1.getAddress()).isNotNull();
        assertThat(customer1.getPhoneNumber()).isNotNull();
        assertThat(customer1.getFirstName()).isEqualTo("Kacper");
        assertThat(customer1.getLastName()).isEqualTo("Florczyk");
        assertThat(customer1.getEmail()).isEqualTo("cos@gmail.com");
        assertThat(customer1.getAddress()).isEqualTo("Staffa");
        assertThat(customer1.getPhoneNumber()).isEqualTo(123123123L);
    }

    @DisplayName("JUnit test for deleteCustomer method")
    @Test
    public void testDeleteCustomer(){

        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);

        String username = "Test1";
        when(authentication.getName()).thenReturn(username);
        when(securityContext.getAuthentication()).thenReturn(authentication);

        when(userRepository.findByLogin("Test1")).thenReturn(user1);
        when(customerRepository.findByFirstName("Test1")).thenReturn(customer1);

        boolean result = customerService.deleteCustomer();
        assertTrue(result);

        verify(customerRepository, times(1)).delete(customer1);
        verify(userRepository, times(1)).delete(user1);

    }

}
