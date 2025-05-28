package com.template.security.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PlateNumberDto {
    private String plateNumber;
    private LocalDate issuedDate;
    private boolean inUse;
}

