package com.template.security.controllers;

import com.template.security.entities.User;
import com.template.security.config.JwtUtil;
import com.template.security.repositories.UserRepository;
import com.template.security.dto.LoginRequest;
import com.template.security.dto.LoginResponse;
import com.template.security.dto.SignupRequest;
import com.template.security.dto.SignupResponse;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication Controller", description = "Handles authentication and user management (signup and login).")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/signup")
    @Operation(summary = "User signup", security = @SecurityRequirement(name = ""),
            responses = {
                    @ApiResponse(responseCode = "200", description = "User registered successfully",
                            content = @Content(schema = @Schema(implementation = SignupResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input or duplicate email/national ID")
            })

    public SignupResponse signup(@Valid @RequestBody SignupRequest signupRequest) {
        if (userRepository.findByEmail(signupRequest.getEmail()).isPresent()) {
            logger.warn("Signup attempt with duplicate email: {}", signupRequest.getEmail());
            throw new RuntimeException("Email already exists");
        }

        if (userRepository.findByNationalId(signupRequest.getNationalId()).isPresent()) {
            logger.warn("Signup attempt with duplicate national ID: {}", signupRequest.getNationalId());
            throw new RuntimeException("National ID already exists");
        }

        User user = new User();
        user.setEmail(signupRequest.getEmail());
        user.setName(signupRequest.getName());
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        user.setPhone(signupRequest.getPhone());
        user.setRole(signupRequest.getRole());
        user.setNationalId(signupRequest.getNationalId());

        userRepository.save(user);
        logger.info("User registered successfully: {}", user.getEmail());

        SignupResponse response = new SignupResponse();
        response.setMessage("User registered successfully");
        response.setEmail(user.getEmail());
        response.setName(user.getName());
        response.setRole(user.getRole());
        return response;
    }

    @PostMapping("/login")
    @Operation(summary = "User login", security = @SecurityRequirement(name = ""),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Login successful",
                            content = @Content(schema = @Schema(implementation = LoginResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid credentials"),
                    @ApiResponse(responseCode = "404", description = "User not found")
            })
    public LoginResponse login(@Valid @RequestBody LoginRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
        } catch (Exception ex) {
            logger.error("Login failed for email {}: Invalid credentials", request.getEmail());
            throw new RuntimeException("Invalid credentials");
        }

        User existingUser = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> {
                    logger.error("User not found with email: {}", request.getEmail());
                    return new UsernameNotFoundException("User not found with email: " + request.getEmail());
                });
        String token = jwtUtil.generateToken(existingUser);
        logger.info("Login successful for user: {}", existingUser.getEmail());

        LoginResponse response = new LoginResponse();
        response.setMessage("Login successful");
        response.setToken(token);
        return response;
    }
}