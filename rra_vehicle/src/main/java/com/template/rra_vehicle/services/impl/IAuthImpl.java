package com.template.rra_vehicle.services.impl;

import com.template.rra_vehicle.dtos.UserDTO;
import com.template.rra_vehicle.request.LoginRequest;
import com.template.rra_vehicle.request.RegisterRequest;
import com.template.rra_vehicle.response.AuthResponse;

public interface IAuthImpl {

    UserDTO registerUser(RegisterRequest registerRequest);
    AuthResponse login(LoginRequest loginRequest);
}
