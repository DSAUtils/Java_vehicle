package com.template.rra_vehicle.services.impl;

import com.template.rra_vehicle.dtos.UserDTO;
import com.template.rra_vehicle.request.SearchUsersRequest;
import com.template.rra_vehicle.request.UpdateUserRequest;

import java.util.List;
import java.util.UUID;

public interface IUserImpl {

    UserDTO getUser(UUID userId);
    UserDTO updateUser(UUID userId, UpdateUserRequest updateUserRequest);
    void deleteUser(UUID userId);
    List<UserDTO> getAllUsers();
    List<UserDTO> searchUsers(SearchUsersRequest searchUsersRequest);

}
