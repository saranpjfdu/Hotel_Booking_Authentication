package com.example.hcl_hack_bakend.common.dto.request;


import lombok.Data;

@Data
public class RegisterRequestDTO {

    private String username;

    private String email;

    private String phoneNumber;

    private String password;
}