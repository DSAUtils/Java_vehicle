package com.template.rra_vehicle.response;

import com.template.rra_vehicle.dtos.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AuthResponse {

    private String token;
    private UserDTO user;

}