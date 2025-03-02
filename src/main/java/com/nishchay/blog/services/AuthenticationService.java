package com.nishchay.blog.services;

import com.nishchay.blog.domain.dtos.UserSignUpDTo;
import org.springframework.security.core.userdetails.UserDetails;

public interface AuthenticationService {

    UserDetails authenticate(String email, String password);
    String generateToken(UserDetails userDetails );

    UserDetails validateToken(String token);

    Void registerUser(UserSignUpDTo userSignUpDTo);
}
