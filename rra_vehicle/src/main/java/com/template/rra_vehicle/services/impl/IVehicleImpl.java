package com.template.rra_vehicle.services.impl;

import com.template.rra_vehicle.dtos.VehicleDTO;
import com.template.rra_vehicle.request.RegisterVehicleRequest;
import com.template.rra_vehicle.request.SearchVehiclesRequest;
import com.template.rra_vehicle.request.UpdateVehicleRequest;

import java.util.List;
import java.util.UUID;

public interface IVehicleImpl {

    VehicleDTO registerVehicle(RegisterVehicleRequest registerVehicleRequest);
    VehicleDTO getVehicle(UUID vehicleId);
    VehicleDTO getVehicleByChassisNumber(String chassisNumber);
    VehicleDTO updateVehicle(UUID vehicleId, UpdateVehicleRequest updateVehicleRequest);
    void deleteVehicle(UUID vehicleId);
    List<VehicleDTO> getAllVehicles();
    List<VehicleDTO> getUserVehicles(UUID userId);
    VehicleDTO getVehicleByPlateNumber(String plateNumber);
    List<VehicleDTO> searchVehicles(SearchVehiclesRequest searchVehiclesRequest);

}
