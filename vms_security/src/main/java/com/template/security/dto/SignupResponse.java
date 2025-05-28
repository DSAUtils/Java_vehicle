package com.template.security.dto;

import lombok.Data;

@Data
public class SignupResponse {
    private String message;
    private String email;
    private String name;
    private String role;
}
