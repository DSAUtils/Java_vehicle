package com.template.security.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity(name = "users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String phone;
    private String nationalId;
    private String password;
    private String role;
}
