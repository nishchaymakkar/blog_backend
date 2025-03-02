package com.nishchay.blog.services.impl;

import com.nishchay.blog.domain.dtos.UserSignUpDTo;
import com.nishchay.blog.domain.entities.User;
import com.nishchay.blog.repository.UserRepository;
import com.nishchay.blog.services.AuthenticationService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl  implements AuthenticationService {
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;

    @Value("${jwt.secret}")
    private String secretKey;

    private final Long jwtExpiryMs = 86400000L;


    @Override
    public UserDetails authenticate(String email, String password) {
       authenticationManager.authenticate(
               new UsernamePasswordAuthenticationToken(email,password)
       );
      return userDetailsService.loadUserByUsername(email);
    }

    @Override
    public UserDetails validateToken(String token) {
        String username = extractUsername(token);
        return userDetailsService.loadUserByUsername(username);
    }

    private String extractUsername(String token){
       Claims claims = Jwts.parser()
               .setSigningKey(getSigningKey())
               .build()
               .parseClaimsJws(token)
               .getBody();
       return claims.getSubject();
    }

    @Override
    public Void registerUser(UserSignUpDTo userSignUpDTo) {

        User newUser = User.builder()
                .name(userSignUpDTo.getUserName())
                .email(userSignUpDTo.getUserEmail())
                .profession(userSignUpDTo.getUserProfession())
                .password(bCryptPasswordEncoder.encode(userSignUpDTo.getUserPassword()))
                .build();
        userRepository.save(newUser);

        return null;
    }

    @Override
    public String generateToken(UserDetails userDetails) {
        Map<String,Object> claims = new HashMap<>();
       return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+ jwtExpiryMs ))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();

    }

    private Key getSigningKey() {
        byte [] keyBytes = secretKey.getBytes();
        return Keys.hmacShaKeyFor(keyBytes);
     }
}
