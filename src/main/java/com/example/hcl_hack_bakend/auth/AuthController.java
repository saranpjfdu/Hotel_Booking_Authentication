package com.example.hcl_hack_bakend.auth;

import com.example.hcl_hack_bakend.common.dto.request.LoginRequestDTO;
import com.example.hcl_hack_bakend.common.dto.request.RegisterRequestDTO;
import com.example.hcl_hack_bakend.common.dto.response.ApiResponse;
import com.example.hcl_hack_bakend.common.dto.response.AuthResponseDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ApiResponse<AuthResponseDTO> login(@RequestBody LoginRequestDTO request) {

        AuthResponseDTO data = authService.login(request);

        return ApiResponse.<AuthResponseDTO>builder()
                .success(true)
                .message("Login successful")
                .data(data)
                .build();
    }
    @PostMapping("/register")
    public ApiResponse<Void> register(@RequestBody RegisterRequestDTO request) {

        authService.register(request);

        return ApiResponse.<Void>builder()
                .success(true)
                .message("User registered successfully")
                .data(null)
                .build();
    }
}