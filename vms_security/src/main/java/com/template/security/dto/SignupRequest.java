package com.template.security.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class SignupRequest {

    @NotBlank(message = "Name is required")
    @Size(max = 100, message = "Name must not exceed 100 characters")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "\\d{10}", message = "Phone number must be 10 digits")
    private String phone;

    @NotBlank(message = "National ID is required")
    @Pattern(regexp = "\\d{16}", message = "National ID must be 16 digits")
    private String nationalId;

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be atleast 8 characters")
    private String password;

    @NotBlank(message = "Role is required")
    @Pattern(regexp = "(ADMIN|STANDARD)", message = "Role must be ADMIN or STANDARD")
    private String role;
}
