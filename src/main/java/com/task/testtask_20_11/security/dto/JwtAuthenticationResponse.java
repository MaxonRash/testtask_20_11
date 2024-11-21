package com.task.testtask_20_11.security.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Response containing JWT token")
public class JwtAuthenticationResponse {
    @Schema(description = "JWT token", example = "asdASh51Fhfkfhlkj.weNlifu67LFh8KJHh...")
    private String token;
}
