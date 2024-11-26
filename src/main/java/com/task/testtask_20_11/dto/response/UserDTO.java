package com.task.testtask_20_11.dto.response;

import com.task.testtask_20_11.security.entity.User;
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