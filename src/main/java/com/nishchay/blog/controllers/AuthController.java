package com.nishchay.blog.controllers;

import com.nishchay.blog.domain.dtos.AuthResponse;
import com.nishchay.blog.domain.dtos.LoginRequest;
import com.nishchay.blog.domain.dtos.UserSignUpDTo;
import com.nishchay.blog.domain.entities.User;
import com.nishchay.blog.services.AuthenticationService;
import com.nishchay.blog.services.UserService;
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
public class AuthController {

    private final AuthenticationService authenticationService;
    private final UserService userService;

    @PostMapping(path = "/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest){
      UserDetails userDetails = authenticationService.authenticate(
                loginRequest.getEmail(),
                loginRequest.getPassword()
        );

       String tokenValue = authenticationService.generateToken(userDetails);
        User userId = userService.getUserIdByEmail(loginRequest.getEmail());
       AuthResponse authResponse =AuthResponse.builder()
                                               .token(tokenValue)
                                               .expiresIn(86400)
                                               .userId(userId.getId())
                                               .build();

       return ResponseEntity.ok(authResponse);
    }



    @PostMapping("/userRegister")
    public ResponseEntity<?> registerUser(@RequestBody UserSignUpDTo userSignUpDto){
        try {
            authenticationService.registerUser(userSignUpDto);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
