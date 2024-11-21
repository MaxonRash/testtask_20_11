package com.task.testtask_20_11.controller;

import com.task.testtask_20_11.security.dto.AuthenticationRequest;
import com.task.testtask_20_11.security.dto.JwtAuthenticationResponse;
import com.task.testtask_20_11.security.dto.RegistrationRequest;
import com.task.testtask_20_11.security.service.AuthenticationService;
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
