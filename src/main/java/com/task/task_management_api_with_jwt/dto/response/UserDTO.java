package com.task.task_management_api_with_jwt.dto.response;

import com.task.task_management_api_with_jwt.security.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Long id;
    private String email;
    private String role;

    public static UserDTO userToUserDTOConverter(User user) {
        UserDTO userDTO = UserDTO.builder().id(user.getId()).email(user.getEmail()).role(user.getRole().toString()).build();
        return userDTO;
    }

}