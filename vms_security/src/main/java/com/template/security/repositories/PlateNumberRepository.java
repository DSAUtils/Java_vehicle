package com.template.security.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.template.security.entities.PlateNumber;
import com.template.security.entities.VehicleOwner;

import java.util.List;

public interface PlateNumberRepository extends JpaRepository<PlateNumber, Long> {
    List<PlateNumber> findByOwner(VehicleOwner owner);
}
