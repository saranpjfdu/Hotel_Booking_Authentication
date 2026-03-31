package com.example.hcl_hack_bakend.auth.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {
    private static final String SECRET =
            "b7f3c9d4e6a1f8b2c5d9e3f7a4b6c1d8e9f2a5b7c3d6e1f4a8b9c2d5e7f1a3b";

    private final SecretKey key = Keys.hmacShaKeyFor(SECRET.getBytes());

    public String generateToken(String email){

        Map<String,Object> claims = new HashMap<>();

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(email)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000*60*60*24))
                .signWith(key, io.jsonwebtoken.SignatureAlgorithm.HS256)
                .compact();
    }

    private Claims extractAllClaims(String token){

        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String extractUsername(String token){
        return extractAllClaims(token).getSubject();
    }

    private Date extractExpiration(String token){
        return extractAllClaims(token).getExpiration();
    }

    private boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    public boolean validateToken(String token, UserDetails userDetails){

        System.out.println("DB username: " + userDetails.getUsername());
        System.out.println("Token expired: " + isTokenExpired(token));




        String username = extractUsername(token);
        System.out.println("Token username: " + username);

        return username.equals(userDetails.getUsername())
                && !isTokenExpired(token);
    }
}