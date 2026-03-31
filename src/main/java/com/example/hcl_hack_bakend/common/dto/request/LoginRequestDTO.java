package com.example.hcl_hack_bakend.common.dto.request;

import lombok.Data;

@Data
public class LoginRequestDTO {

    private String email;
    private String password;
}