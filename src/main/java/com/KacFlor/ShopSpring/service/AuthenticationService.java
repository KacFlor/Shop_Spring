package com.KacFlor.ShopSpring.service;

import com.KacFlor.ShopSpring.dao.CustomerRepository;
import com.KacFlor.ShopSpring.dao.UserRepository;
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

    private final AuthenticationManager authenticationManager;

    private final BCryptPasswordEncoder passwordEncoder;

    public AuthenticationResponse register(RegisterRequest request){
        var customer = new Customer("Adam", "Florczyk", "kacper.florczyk@onet.pl", "staffa");
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        var user = new User(request.getLogin(), encodedPassword, customer, Role.USER);
        userRepository.save(user);
        customerRepository.save(customer);
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
