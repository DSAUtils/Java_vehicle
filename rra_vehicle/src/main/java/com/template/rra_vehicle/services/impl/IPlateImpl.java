package com.template.rra_vehicle.services.impl;

import com.template.rra_vehicle.dtos.PlateDTO;
import com.template.rra_vehicle.request.RegisterPlateRequest;
import com.template.rra_vehicle.request.UpdatePlateRequest;

import java.util.List;
import java.util.UUID;

public interface IPlateImpl {

    PlateDTO registerPlate(RegisterPlateRequest registerPlateRequest);
    void deletePlate(UUID plateId);
    PlateDTO updatePlate(UUID plateId, UpdatePlateRequest updatePlateRequest);

    PlateDTO getPlate(UUID plateId);
    PlateDTO getPlateByNumber(String number);
    List<PlateDTO> getAllPlates();
    List<PlateDTO> getUserPlates(UUID userId);

}
