package com.KacFlor.ShopSpring.controller;

import com.KacFlor.ShopSpring.model.Role;
import com.KacFlor.ShopSpring.security.AuthenticationRequest;
import com.KacFlor.ShopSpring.security.AuthenticationResponse;
import com.KacFlor.ShopSpring.service.AuthenticationService;
import com.KacFlor.ShopSpring.security.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authentication")
@RequiredArgsConstructor
public class AuthenticationController{

    private final AuthenticationService service;

    @PostMapping("/register-user")
    public ResponseEntity<AuthenticationResponse> registerUser(
            @RequestBody RegisterRequest request
    ){
        return ResponseEntity.ok(service.register(request, Role.USER));

    }

    @PreAuthorize("hasAuthority('" + Role.Fields.ADMIN + "')")
    @PostMapping("/register-admin")
    public ResponseEntity<AuthenticationResponse> registerAdmin(
            @RequestBody RegisterRequest request
    ){
        return ResponseEntity.ok(service.register(request, Role.ADMIN));

    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ){
        return ResponseEntity.ok(service.authenticate(request));

    }

}
