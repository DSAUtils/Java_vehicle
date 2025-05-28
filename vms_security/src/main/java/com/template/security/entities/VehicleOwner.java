package com.template.security.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class VehicleOwner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String nationalId;
    private String phone;
    private String address;
}
