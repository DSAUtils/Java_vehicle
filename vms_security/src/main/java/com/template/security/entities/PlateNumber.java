package com.template.security.entities;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Data
public class PlateNumber {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private VehicleOwner owner;
    private String plateNumber;
    private LocalDate issuedDate;
    private boolean inUse;
}
