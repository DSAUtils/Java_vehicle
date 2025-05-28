package com.template.security.controllers;

import com.template.security.dto.PlateNumberDto;
import com.template.security.dto.VehicleOwnerDto;
import com.template.security.entities.PlateNumber;
import com.template.security.entities.VehicleOwner;
import com.template.security.repositories.PlateNumberRepository;
import com.template.security.repositories.VehicleOwnerRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import java.util.List;

@RestController
@RequestMapping("/api/owners")
@Tag(name = "Owner Controller", description = "Vehicle owner management")
@RequiredArgsConstructor
public class OwnerController {

    private final VehicleOwnerRepository ownerRepository;
    private final PlateNumberRepository plateNumberRepository;

    private VehicleOwnerDto toDto(VehicleOwner owner) {
        VehicleOwnerDto dto = new VehicleOwnerDto();
        dto.setName(owner.getName());
        dto.setNationalId(owner.getNationalId());
        dto.setPhone(owner.getPhone());
        dto.setAddress(owner.getAddress());
        return dto;
    }

    private VehicleOwner toEntity(VehicleOwnerDto dto) {
        VehicleOwner owner = new VehicleOwner();
        owner.setName(dto.getName());
        owner.setNationalId(dto.getNationalId());
        owner.setPhone(dto.getPhone());
        owner.setAddress(dto.getAddress());
        return owner;
    }

    @Operation(summary = "Register a new vehicle owner")
    @PostMapping
    public VehicleOwnerDto registerOwner(@RequestBody VehicleOwnerDto ownerDto) {
        VehicleOwner saved = ownerRepository.save(toEntity(ownerDto));
        return toDto(saved);
    }

    @Operation(summary = "Get paginated list of vehicle owners")
    @GetMapping
    public Page<VehicleOwnerDto> getOwners(@RequestParam int page, @RequestParam int size) {
        return ownerRepository.findAll(PageRequest.of(page, size)).map(this::toDto);
    }

    @Operation(summary = "Search owner by national ID or phone")
    @GetMapping("/search")
    public VehicleOwnerDto searchOwner(@RequestParam(required = false) String nationalId, @RequestParam(required = false) String phone) {
        VehicleOwner owner = null;
        if (nationalId != null) owner = ownerRepository.findByNationalId(nationalId);
        else if (phone != null) owner = ownerRepository.findByPhone(phone);

        if (owner == null) throw new RuntimeException("Owner not found or missing search parameters");
        return toDto(owner);
    }

    @Operation(summary = "Add a plate number to an owner")
    @PostMapping("/{ownerId}/plate")
    public PlateNumberDto registerPlate(@PathVariable Long ownerId, @RequestBody PlateNumberDto plateNumberDto) {
        VehicleOwner owner = ownerRepository.findById(ownerId)
                .orElseThrow(() -> new RuntimeException("Owner not found"));

        PlateNumber plateNumber = new PlateNumber();
        plateNumber.setOwner(owner);
        plateNumber.setPlateNumber(plateNumberDto.getPlateNumber());
        plateNumber.setIssuedDate(plateNumberDto.getIssuedDate());
        plateNumber.setInUse(plateNumberDto.isInUse());

        PlateNumber saved = plateNumberRepository.save(plateNumber);

        PlateNumberDto responseDto = new PlateNumberDto();
        responseDto.setPlateNumber(saved.getPlateNumber());
        responseDto.setIssuedDate(saved.getIssuedDate());
        responseDto.setInUse(saved.isInUse());

        return responseDto;
    }


    @Operation(summary = "Get plates for an owner")
    @GetMapping("/{ownerId}/plates")
    public List<PlateNumberDto> getPlates(@PathVariable Long ownerId) {
        VehicleOwner owner = ownerRepository.findById(ownerId)
                .orElseThrow(() -> new RuntimeException("Owner not found"));

        List<PlateNumber> plates = plateNumberRepository.findByOwner(owner);

        return plates.stream().map(plate -> {
            PlateNumberDto dto = new PlateNumberDto();
            dto.setPlateNumber(plate.getPlateNumber());
            dto.setIssuedDate(plate.getIssuedDate());
            dto.setInUse(plate.isInUse());
            return dto;
        }).toList();
    }
}
