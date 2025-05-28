package com.template.security.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class VehicleOwnerDto {
    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "National ID is required")
    @Pattern(regexp = "\\d{16}", message = "National ID must be 16 digits")
    private String nationalId;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "\\d{10}", message = "Phone number must be 10 digits")
    private String phone;

    @NotBlank(message = "Address is required")
    private String address;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    private String email;
}
