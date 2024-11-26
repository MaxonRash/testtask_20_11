package com.task.testtask_20_11.dto.request;

import com.task.testtask_20_11.entity.Comment;
import com.task.testtask_20_11.security.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Add implementer request")
public class UserRequestDTO {
    @Schema(description = "Email address", example = "max@mail.ru")
    @Email(message = "Email must be valid")
    @NotBlank(message = "Email cannot be empty")
    @Size(min = 5, max = 20, message = "Email length must be between 5 and 20 characters")
    private String email;

    /**
     * Returns new {@link User} with set email field
     * @param userRequestDTO
     * @return {@link User}
     */
    public static User userRequestDTOToUserConverter(UserRequestDTO userRequestDTO) {
        User user = new User();
        user.setEmail(userRequestDTO.getEmail());
        return user;
    }
}
