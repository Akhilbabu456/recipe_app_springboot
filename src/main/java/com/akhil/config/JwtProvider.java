package com.akhil.config;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtProvider {
    
    private SecretKey key = Keys.hmacShaKeyFor(JwtConstant.JWT_SECRET.getBytes());

    public String generateToken(Authentication auth){
        // Assuming you have a UserDetails object as the authentication principal

        // Set JWT claims
        String jwt = Jwts.builder()
            .claim("email", auth.getName())
            .signWith(key).compact(); // Convert JWT builder to string

        return jwt;
    }

    public String getEmailFromJwtToken(String jwt){
        jwt=jwt.substring(7);
        Claims claims= Jwts.parserBuilder()
        .setSigningKey(key)
        .build()
        .parseClaimsJws(jwt)
        .getBody();

        String email = String.valueOf(claims.get("email"));

        return email;
    }
}
