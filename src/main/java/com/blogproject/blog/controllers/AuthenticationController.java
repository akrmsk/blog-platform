package com.blogproject.blog.controllers;


import com.blogproject.blog.domain.dtos.AuthResponse;
import com.blogproject.blog.domain.dtos.LoginRequest;
import com.blogproject.blog.services.AuthenticationService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request){

        UserDetails userDetails=authenticationService.authenticate(
                request.getEmail(),
                request.getPassword()
        );
        String tokenValue=authenticationService.generateToken(userDetails);
        AuthResponse authResponse=AuthResponse.builder()
                .token(tokenValue)
                .expiresIn(86400)
                .build();
        return new ResponseEntity<>(authResponse,HttpStatus.OK);

    }
}
