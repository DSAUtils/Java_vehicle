package com.template.security.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.template.security.entities.User;
import com.template.security.config.JwtUtil;
import com.template.security.repositories.UserRepository;
import com.template.security.dto.LoginRequest;
import com.template.security.dto.LoginResponse;
import com.template.security.dto.SignupRequest;
import com.template.security.dto.SignupResponse;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication Controller", description = "Handles authentication and user management (signup and login).")
public class AuthController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @PostMapping("/signup")
    @Operation(
            summary = "User registration",
            description = "Registers a new user and saves them to the database.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "User registered successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = SignupResponse.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid signup request",
                            content = @Content
                    )
            }
    )
    public SignupResponse signup(@RequestBody SignupRequest signupRequest) {
        User user = new User();
        user.setEmail(signupRequest.getEmail());
        user.setName(signupRequest.getName());
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        user.setPhone(signupRequest.getPhone());
        user.setRole(signupRequest.getRole());
        user.setNationalId(signupRequest.getNationalId());
        userRepository.save(user);

        SignupResponse response = new SignupResponse();
        response.setMessage("User registered successfully");
        response.setEmail(user.getEmail());
        response.setName(user.getName());
        response.setRole(user.getRole());
        return response;
    }

    @PostMapping("/login")
    @Operation(
            summary = "User login",
            description = "Authenticates a user and provides a JWT token upon successful login.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Login successful",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = LoginResponse.class))
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Invalid credentials",
                            content = @Content
                    )
            }
    )
    public LoginResponse login(@RequestBody LoginRequest request) {
        User existingUser = userRepository.findByEmail(request.getEmail());
        if (existingUser != null && passwordEncoder.matches(request.getPassword(), existingUser.getPassword())) {
            String token = jwtUtil.generateToken(existingUser);

            LoginResponse response = new LoginResponse();
            response.setMessage("Login successful");
            response.setToken(token);
            return response;
        }
        throw new RuntimeException("Invalid credentials");
    }

}
