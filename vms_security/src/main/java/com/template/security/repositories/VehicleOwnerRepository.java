package com.template.security.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.template.security.entities.VehicleOwner;

public interface VehicleOwnerRepository extends JpaRepository<VehicleOwner, Long> {
    //    VehicleOwner findByEmail(String email);
    VehicleOwner findByNationalId(String nationalId);
    VehicleOwner findByPhone(String phone);
}
