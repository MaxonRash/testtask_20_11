package com.task.task_management_api_with_jwt.dto.request;

import com.task.task_management_api_with_jwt.entity.Comment;
import com.task.task_management_api_with_jwt.entity.Priority;
import com.task.task_management_api_with_jwt.entity.Task;
import com.task.task_management_api_with_jwt.exception.task.NeedToBeAnImplementerException;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Task creation request")
public class TaskCreateDTO {
    @Schema(description = "Heading", example = "Make a new button")
    @NotBlank(message = "Heading cannot be empty")
    @Size(min = 2, max = 50, message = "Heading length must be between 2 and 50 characters")
    private String heading;

    @Schema(description = "Description", example = "Need to make a new button to redirect to main page...")
    @NotBlank(message = "Description cannot be empty")
    private String description;

    @Schema(description = "Priority", examples = {"LOW", "AVERAGE", "HIGH"})
    @NotNull
    @Pattern(regexp = "LOW|AVERAGE|HIGH", message = "Allowed values are: LOW, AVERAGE, HIGH")
    private String priority;

    @Parameter(description = "Initial comments after creation")
    @Nullable
    @Valid
    private List<CommentRequestDTO> comments;

    @Parameter(description = "Users assigned to this task. Returns Error if one of users doesn't exist")
    @Valid
    @NotEmpty(message = "There must be at least 1 implementer")
    private List<UserRequestDTO> implementers;

    /**
     * Returns new {@link Task} with all field set except Author
     * @param taskCreateDTO
     * @return {@link Task}
     */
    public static Task taskCreateDTOToTaskConverter(TaskCreateDTO taskCreateDTO) {
        List<Comment> comments = new ArrayList<>();
        if (taskCreateDTO.getComments() != null) {
            comments = taskCreateDTO.getComments().stream().map(CommentRequestDTO::commentRequestDTOToCommentConverter).toList();
        }
        if (taskCreateDTO.getImplementers() == null) {
            throw new NeedToBeAnImplementerException();
        }
        Task task = new Task(taskCreateDTO.heading, taskCreateDTO.description, Priority.valueOf(taskCreateDTO.priority),
                comments,
                taskCreateDTO.getImplementers().stream().map(UserRequestDTO::userRequestDTOToUserConverter).toList());
        return task;
    }
}
