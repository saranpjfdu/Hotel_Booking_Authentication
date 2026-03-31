package com.example.hcl_hack_bakend.auth;

import com.example.hcl_hack_bakend.auth.jwt.JwtService;
import com.example.hcl_hack_bakend.common.dto.request.LoginRequestDTO;
import com.example.hcl_hack_bakend.common.dto.request.RegisterRequestDTO;
import com.example.hcl_hack_bakend.common.dto.response.AuthResponseDTO;
import com.example.hcl_hack_bakend.user.enums.Role;
import com.example.hcl_hack_bakend.user.repo.UserRepo;
import com.example.hcl_hack_bakend.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
public class AuthService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    // 🔐 LOGIN
    public AuthResponseDTO login(LoginRequestDTO loginDto) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getEmail(),
                        loginDto.getPassword()
                )
        );

        if (authentication.isAuthenticated()) {

            String token = jwtService.generateToken(loginDto.getEmail());

            return new AuthResponseDTO(
                    token,
                    "Login Successful"
            );
        }

        throw new RuntimeException("Invalid credentials");
    }

    // 📝 REGISTER
    public String register(RegisterRequestDTO dto) {

        if (userRepo.existsByEmail(dto.getEmail())) {
            return "Email already registered";
        }

        if (userRepo.existsByUsername(dto.getUsername())) {
            return "Username already taken";
        }

        User user = User.builder()
                .username(dto.getUsername())
                .email(dto.getEmail())
                .phoneNumber(dto.getPhoneNumber())
                .password(passwordEncoder.encode(dto.getPassword()))
                .role(Role.USER)
                .enabled(true)
                .accountNonLocked(true)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        userRepo.save(user);

        return "User Registered Successfully";
    }
}