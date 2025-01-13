package com.task.task_management_api_with_jwt.controller;

import com.task.task_management_api_with_jwt.security.dto.AuthenticationRequest;
import com.task.task_management_api_with_jwt.security.dto.JwtAuthenticationResponse;
import com.task.task_management_api_with_jwt.security.dto.RegistrationRequest;
import com.task.task_management_api_with_jwt.security.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @Operation(summary = "User registration")
    @PostMapping("/register")
    public JwtAuthenticationResponse register(@RequestBody @Valid RegistrationRequest request) {
        return authenticationService.register(request);
    }

    @Operation(summary = "User authentication")
    @PostMapping("/login")
    public JwtAuthenticationResponse login(@RequestBody @Valid AuthenticationRequest request) {
        return authenticationService.login(request);
    }
}
