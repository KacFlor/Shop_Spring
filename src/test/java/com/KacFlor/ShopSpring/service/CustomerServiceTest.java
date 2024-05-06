package com.KacFlor.ShopSpring.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.KacFlor.ShopSpring.config.ExceptionsConfig;
import com.KacFlor.ShopSpring.controllersRequests.CustomerUpdateRequest;
import com.KacFlor.ShopSpring.controllersRequests.NewOrder;
import com.KacFlor.ShopSpring.controllersRequests.NewShipment;
import com.KacFlor.ShopSpring.dao.CustomerRepository;
import com.KacFlor.ShopSpring.dao.OrderRepository;
import com.KacFlor.ShopSpring.dao.ShipmentRepository;
import com.KacFlor.ShopSpring.dao.UserRepository;
import com.KacFlor.ShopSpring.model.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

import org.springframework.security.core.Authentication;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest{

    @Mock
    private ShipmentRepository shipmentRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CustomerService customerService;

    @DisplayName("JUnit test for CreateShipment method")
    @Test
    public void testCreateShipment(){

        NewShipment newShipment = new NewShipment();
        newShipment.setShipmentDate(LocalDate.of(2024, 5, 1));
        newShipment.setAddress("Test Address");
        newShipment.setCity("Test City");
        newShipment.setState("Test State");
        newShipment.setCountry("Test Country");
        newShipment.setZipcode("12345");

        Customer customer1 = new Customer();

        User user1 = new User("Test1", "encodedPassword1", customer1, Role.USER);

        customer1.setUser(user1);
        customer1.setFirstName("Test1");
        customer1.setShipments(new ArrayList<>());

        Shipment shipment = new Shipment(newShipment.getShipmentDate(), newShipment.getAddress(), newShipment.getCity(), newShipment.getState(), newShipment.getCountry(), newShipment.getZipcode());

        customer1.getShipments().add(shipment);
        shipment.setCustomer(customer1);

        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);

        String username = "Test1";
        when(authentication.getName()).thenReturn(username);
        when(securityContext.getAuthentication()).thenReturn(authentication);

        when(userRepository.findByLogin(username)).thenReturn(user1);

        boolean result = customerService.createShipment(newShipment);
        assertTrue(result);

        verify(userRepository, times(1)).findByLogin(username);
        verify(customerRepository, times(1)).save(customer1);
        verify(shipmentRepository, times(1)).save(any(Shipment.class));

    }

    @DisplayName("JUnit test for getById method")
    @Test
    public void testGetById(){
        Customer customer1 = new Customer();

        User user1 = new User("Test1", "encodedPassword1", customer1, Role.USER);

        customer1.setUser(user1);
        customer1.setFirstName("Test1");

        Integer customerId = 1;

        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer1));

        Customer actualCustomer = customerService.getCustomerById(customerId);

        assertEquals(customer1, actualCustomer);

        verify(customerRepository, times(1)).findById(customerId);

        when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

        assertThrows(ExceptionsConfig.ResourceNotFoundException.class, () -> customerService.getCustomerById(customerId));

        verify(customerRepository, times(2)).findById(customerId);

    }

    @DisplayName("JUnit test for deleteById method")
    @Test
    public void testDeleteById(){
        Customer customer1 = new Customer();

        User user1 = new User("Test1", "encodedPassword1", customer1, Role.USER);

        customer1.setUser(user1);
        customer1.setFirstName("Test1");

        Integer CustomerId = 2;

        when(customerRepository.findById(CustomerId)).thenReturn(Optional.of(customer1));
        when(userRepository.findById(CustomerId)).thenReturn(Optional.of(user1));

        boolean result = customerService.deleteCustomerById(CustomerId);
        assertTrue(result);

        verify(customerRepository, times(1)).delete(customer1);
        verify(userRepository, times(1)).delete(user1);
    }

    @DisplayName("JUnit test getCustomersList method")
    @Test
    public void testGetCustomersList(){
        Customer customer1 = new Customer();
        Customer customer2 = new Customer();
        Customer customer = new Customer();

        PasswordEncoder encoder = new BCryptPasswordEncoder();
        User user1 = new User("Test1", "encodedPassword1", customer1, Role.USER);
        User user2 = new User("Test2", "encodedPassword2", customer2, Role.USER);
        User hAdmin = new User("HAdmin", encoder.encode("1234"), customer, Role.ADMIN);

        customer1.setUser(user1);
        customer2.setUser(user2);
        customer.setUser(hAdmin);
        customer1.setFirstName("Test1");
        customer2.setFirstName("Test2");
        customer.setFirstName("HAdmin");

        given(customerRepository.findAll()).willReturn(List.of(customer1, customer2, customer));

        List<Customer> result = customerService.getAll();

        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(3);

        assertThat(result.get(0).getFirstName()).isEqualTo("Test1");
        assertThat(result.get(1).getFirstName()).isEqualTo("Test2");
        assertThat(result.get(2).getFirstName()).isEqualTo("HAdmin");
    }

    @DisplayName("JUnit test for getCustomer method")
    @Test
    public void testGetCustomer(){
        Customer customer1 = new Customer();

        User user1 = new User("Test1", "encodedPassword1", customer1, Role.USER);

        customer1.setUser(user1);
        customer1.setFirstName("Test1");

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
        Customer customer1 = new Customer();

        User user1 = new User("Test1", "encodedPassword1", customer1, Role.USER);

        customer1.setUser(user1);
        customer1.setFirstName("Test1");

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
        customerUpdateRequest.setLastName("Florry");
        customerUpdateRequest.setEmail("cos@gmail.com");
        customerUpdateRequest.setAddress("Staff");
        customerUpdateRequest.setPhoneNumber(123123123L);

        boolean result = customerService.updateCustomer(customerUpdateRequest);
        assertTrue(result);

        verify(customerRepository).save(customer1);

        assertThat(customer1.getFirstName()).isNotNull();
        assertThat(customer1.getLastName()).isNotNull();
        assertThat(customer1.getEmail()).isNotNull();
        assertThat(customer1.getAddress()).isNotNull();
        assertThat(customer1.getPhoneNumber()).isNotNull();
        assertThat(customer1.getFirstName()).isEqualTo("Kacper");
        assertThat(customer1.getLastName()).isEqualTo("Florry");
        assertThat(customer1.getEmail()).isEqualTo("cos@gmail.com");
        assertThat(customer1.getAddress()).isEqualTo("Staff");
        assertThat(customer1.getPhoneNumber()).isEqualTo(123123123L);
    }

    @DisplayName("JUnit test for deleteCustomer method")
    @Test
    public void testDeleteCustomer(){
        Customer customer1 = new Customer();

        User user1 = new User("Test1", "encodedPassword1", customer1, Role.USER);

        customer1.setUser(user1);
        customer1.setFirstName("Test1");

        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);

        String username = "Test1";
        when(authentication.getName()).thenReturn(username);
        when(securityContext.getAuthentication()).thenReturn(authentication);

        when(userRepository.findByLogin("Test1")).thenReturn(user1);
        when(customerRepository.findByFirstName("Test1")).thenReturn(customer1);

        boolean result = customerService.deleteCurrentCustomer();
        assertTrue(result);

        verify(customerRepository, times(1)).delete(customer1);
        verify(userRepository, times(1)).delete(user1);

    }

}
