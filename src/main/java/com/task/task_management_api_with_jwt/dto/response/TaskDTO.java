package com.task.task_management_api_with_jwt.dto.response;

import com.task.task_management_api_with_jwt.entity.Task;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.SimpleDateFormat;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TaskDTO {
    private Long id;
    private String heading;
    private String description;
    private String createdAt;
    private String status;
    private String priority;
    private List<CommentResponseDTO> comments;
    private UserDTO author;
    private List<UserDTO> implementers;

    public static TaskDTO TaskToTaskDTOConverter(Task task) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        TaskDTO taskDTO = TaskDTO.builder().id(task.getId()).heading(task.getHeading()).description(task.getDescription())
                .createdAt(sdf.format(task.getCreatedAt().getTime())).status(task.getStatus().getText()).priority(task.getPriority().getText())
                .comments(task.getComments().stream().map(CommentResponseDTO::commentToCommentDTOConverter).toList())
                .author(UserDTO.userToUserDTOConverter(task.getAuthor()))
                .implementers(task.getImplementers().stream().map(UserDTO::userToUserDTOConverter).toList()).build();
        return taskDTO;
    }
}
