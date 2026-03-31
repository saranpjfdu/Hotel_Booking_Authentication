package com.example.hcl_hack_bakend.auth.jwt;

import com.example.hcl_hack_bakend.auth.MyUserDetailService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class Jwtfilter extends OncePerRequestFilter {

    @Autowired
    JwtService jwtService;

    @Autowired
    MyUserDetailService myUserDetailService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String token = null;
        String email = null;

        System.out.println("JWT FILTER EXECUTED");

        // ✅ Get Authorization Header
        final String authHeader = request.getHeader("Authorization");

        // ✅ Check Bearer token
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7); // remove "Bearer "
            System.out.println("Token from header: " + token);
        }

        // ✅ Extract username (email)
        if (token != null) {
            try {
                email = jwtService.extractUsername(token);
            } catch (Exception e) {
                System.out.println("Invalid JWT token");
            }
        }

        // ✅ Authenticate user
        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = myUserDetailService.loadUserByUsername(email);

            if (jwtService.validateToken(token, userDetails)) {

                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );

                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                SecurityContextHolder.getContext().setAuthentication(authToken);
                System.out.println("Authentication set successfully");
            }
        }

        filterChain.doFilter(request, response);
    }
}