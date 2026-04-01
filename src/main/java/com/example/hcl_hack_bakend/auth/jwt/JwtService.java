package com.example.hcl_hack_bakend.auth.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {

    // Ensure your secret is at least 256 bits (32 characters)
    private static final String SECRET =
            "b7f3c9d4e6a1f8b2c5d9e3f7a4b6c1d8e9f2a5b7c3d6e1f4a8b9c2d5e7f1a3b";

    private final SecretKey key = Keys.hmacShaKeyFor(SECRET.getBytes());

    // ✅ NEW: Generate token directly from UserDetails (Secure role extraction)
    public String generateToken(UserDetails userDetails) {
        // Safely extract the role granted by Spring Security from the database
        String role = userDetails.getAuthorities().stream()
                .findFirst()
                .map(GrantedAuthority::getAuthority)
                .orElse("USER"); // Fallback if no roles are found

        System.out.println("ROLE EXTRACTED FROM DB: " + role);

        // Pass it to the actual builder method
        return generateToken(userDetails.getUsername(), role);
    }

    // 🔥 UPDATED: Include role in token creation
    public String generateToken(String email, String role){

        System.out.println("ROLE GOING INTO TOKEN: " + role); // optional debug

        Map<String,Object> claims = new HashMap<>();
        claims.put("role", role); // ✅ MUST KEEP

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(email)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24)) // 24 hours
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

    // ✅ NEW: extract role
    public String extractRole(String token){
        return (String) extractAllClaims(token).get("role");
    }

    private Date extractExpiration(String token){
        return extractAllClaims(token).getExpiration();
    }

    private boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    public boolean validateToken(String token, UserDetails userDetails){

        String username = extractUsername(token);

        System.out.println("DB username: " + userDetails.getUsername());
        System.out.println("Token username: " + username);
        System.out.println("Token role: " + extractRole(token));
        System.out.println("Token expired: " + isTokenExpired(token));

        return username.equals(userDetails.getUsername())
                && !isTokenExpired(token);
    }
}