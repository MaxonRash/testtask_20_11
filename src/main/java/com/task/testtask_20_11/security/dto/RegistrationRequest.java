package com.task.testtask_20_11.security.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Registration request")
public class RegistrationRequest {
    @Schema(description = "Email address", example = "max@mail.ru")
    @Email(message = "Email must be valid")
    @NotBlank(message = "Email cannot be empty")
    @Size(min = 5, max = 20, message = "Email length must be between 5 and 20 characters")
    private String email;

    @Schema(description = "Password", example = "my_password132")
    @NotBlank(message = "Password cannot be empty")
    @Size(min = 5, max = 20, message = "Password length must be between 5 and 20 characters")
    private String password;
}
