package com.template.security.dto;

import lombok.Data;

@Data
public class SignupRequest {
    private String name;
    private String email;
    private String phone;
    private String nationalId;
    private String password;
    private String role;
}
