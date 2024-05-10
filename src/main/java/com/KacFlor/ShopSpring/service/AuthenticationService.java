package com.KacFlor.ShopSpring.service;

import com.KacFlor.ShopSpring.dao.CartRepository;
import com.KacFlor.ShopSpring.dao.CustomerRepository;
import com.KacFlor.ShopSpring.dao.UserRepository;
import com.KacFlor.ShopSpring.model.Cart;
import com.KacFlor.ShopSpring.model.Customer;
import com.KacFlor.ShopSpring.model.Role;
import com.KacFlor.ShopSpring.model.User;
import com.KacFlor.ShopSpring.security.AuthenticationRequest;
import com.KacFlor.ShopSpring.security.AuthenticationResponse;
import com.KacFlor.ShopSpring.security.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService{

    private final JwtService jwtService;

    private final UserRepository userRepository;

    private final CustomerRepository customerRepository;

    private final CartRepository cartRepository;

    private final AuthenticationManager authenticationManager;

    private final BCryptPasswordEncoder passwordEncoder;

    public AuthenticationResponse register(RegisterRequest request, Role role){
        Cart cart = new Cart();
        var customer = new Customer();
        cartRepository.save(cart);
        customer.setCart(cart);
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        var user = new User(request.getLogin(), encodedPassword, customer, role);
        userRepository.save(user);
        customer.setUser(user);
        customer.setFirstName(request.getLogin());
        customerRepository.save(customer);
        cart.setCustomer(customer);
        cartRepository.save(cart);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getLogin(),
                        request.getPassword()
                )
        );

        var user = userRepository.findByLogin(request.getLogin());
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }


}
