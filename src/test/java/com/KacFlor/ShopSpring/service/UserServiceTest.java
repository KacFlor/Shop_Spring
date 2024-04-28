package com.KacFlor.ShopSpring.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.KacFlor.ShopSpring.dao.UserRepository;
import com.KacFlor.ShopSpring.model.Customer;
import com.KacFlor.ShopSpring.model.Role;
import com.KacFlor.ShopSpring.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest{

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User user1;
    private User user2;

    private User hAdmin;

    @BeforeEach
    public void setup() {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        user1 = new User("Test1", "encodedPassword1", new Customer(), Role.USER);
        user2 = new User("Test2", "encodedPassword2", new Customer(), Role.USER);
        hAdmin = new User("HAdmin",  encoder.encode("1234"), new Customer(), Role.ADMIN);
    }

    @DisplayName("JUnit test for getAllUsers method")
    @Test
    public void testGetAllUsers(){

        User user3 = new User("Test3", "encodedPassword3", new Customer(), Role.USER);

        given(userRepository.findAll()).willReturn(List.of(user1,user2,user3,hAdmin));

        List<User> result = userService.getAllUsers();

        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(4);

        assertThat(result.get(0).getLogin()).isEqualTo("Test1");
        assertThat(result.get(1).getLogin()).isEqualTo("Test2");
        assertThat(result.get(2).getLogin()).isEqualTo("Test3");
        assertThat(result.get(3).getLogin()).isEqualTo("HAdmin");
    }

    @DisplayName("JUnit test for getCurrentUser method")
    @Test
    public void testGetCurrentUser(){

        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);

        UserDetails userDetails = Mockito.mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn("Test1");

        Authentication authentication = Mockito.mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(securityContext.getAuthentication()).thenReturn(authentication);

        when(userRepository.findByLogin("Test1")).thenReturn(user1);

        User currentUser = userService.getCurrentUser();

        assertThat(currentUser).isNotNull();
        assertThat(currentUser.getLogin()).isEqualTo(user1.getLogin());
    }

    @DisplayName("JUnit test for changeName method")
    @Test
    public void testchangeName(){

        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);

        UserDetails userDetails = Mockito.mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn("Test1");

        Authentication authentication = Mockito.mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(securityContext.getAuthentication()).thenReturn(authentication);

        when(userRepository.findByLogin("Test1")).thenReturn(user1);

        String newName = "1resu";
        boolean result = userService.changeName(newName);
        assertTrue(result);

        verify(userRepository).save(user1);

        assertThat(user1.getLogin()).isNotNull();
        assertThat(user1.getLogin()).isEqualTo(newName);
    }

    @DisplayName("JUnit test for deleteUserById method")
    @Test
    public void testDeleteUserById(){

        Integer UserId = 2;

        when(userRepository.findById(UserId)).thenReturn(Optional.ofNullable(user1));

        boolean result = userService.deleteUserById(UserId);
        assertTrue(result);

        // then - verify the output
        verify(userRepository, times(1)).delete(user1);

    }

    @DisplayName("JUnit test for deleteUser method")
    @Test
    public void testDeleteUser(){

        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);

        UserDetails userDetails = Mockito.mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn("Test1");

        Authentication authentication = Mockito.mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(securityContext.getAuthentication()).thenReturn(authentication);

        when(userRepository.findByLogin("Test1")).thenReturn(user1);

        boolean result = userService.deleteUser();
        assertTrue(result);

        verify(userRepository, times(1)).delete(user1);

    }

}
