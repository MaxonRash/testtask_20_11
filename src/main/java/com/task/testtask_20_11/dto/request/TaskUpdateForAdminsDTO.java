package com.task.testtask_20_11.dto.request;

import com.task.testtask_20_11.entity.Comment;
import com.task.testtask_20_11.entity.Priority;
import com.task.testtask_20_11.entity.Status;
import com.task.testtask_20_11.entity.Task;
import com.task.testtask_20_11.exception.task.for_admins.DescriptionCannotBeBlankException;
import com.task.testtask_20_11.exception.task.for_admins.HeadingCannotBeBlankException;
import com.task.testtask_20_11.exception.task.for_admins.HeadingLengthIsNotSupportedException;
import com.task.testtask_20_11.exception.task.for_admins.ImplementersCannotBeEmptyException;
import com.task.testtask_20_11.security.entity.User;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Only for users having admin role: update task or get task by Id")
public class TaskUpdateForAdminsDTO {

    @Schema(description = "Heading", example = "Make a new button")
    @Nullable
    private String heading;

    @Schema(description = "Description", example = "Need to make a new button to redirect to main page...")
    @Nullable
    private String description;

    @Schema(description = "Status", examples = {"WAITING", "IN_PROGRESS", "DONE"})
    @Pattern(regexp = "WAITING|IN_PROGRESS|DONE", message = "Allowed values are: WAITING, IN_PROGRESS, DONE")
    @Nullable
    private String status;

    @Schema(description = "Priority", examples = {"LOW", "AVERAGE", "HIGH"})
    @Pattern(regexp = "LOW|AVERAGE|HIGH", message = "Allowed values are: LOW, AVERAGE, HIGH")
    @Nullable
    private String priority;

    @Parameter(description = "Add new comment to this task")
    @Nullable
    @Valid
    private List<CommentRequestDTO> comments;

    @Parameter(description = "Users assigned to this task. Returns Error if one of users doesn't exist")
    @Nullable
    @Valid
    private List<UserRequestDTO> implementers;

    /**
     * Returns new {@link Task} with all field set except Author
     * @param taskUpdateForAdminsDTO
     * @return {@link Task}
     */
    public static Task taskUpdateForAdminsDTOToTaskConverter(TaskUpdateForAdminsDTO taskUpdateForAdminsDTO) {
        List<Comment> comments = null;
        if (taskUpdateForAdminsDTO.getComments() != null) {
            comments = taskUpdateForAdminsDTO.getComments().stream().map(CommentRequestDTO::commentRequestDTOToCommentConverter).toList();
        }
        List<User> implementers = null;
        if (taskUpdateForAdminsDTO.getImplementers() != null) {
            if (taskUpdateForAdminsDTO.getImplementers().isEmpty()) {
                throw new ImplementersCannotBeEmptyException();
            }
            implementers = taskUpdateForAdminsDTO.getImplementers().stream().map(UserRequestDTO::userRequestDTOToUserConverter).toList();
        }
        String heading = null;
        if (taskUpdateForAdminsDTO.getHeading() != null) {
            if (taskUpdateForAdminsDTO.getHeading().isBlank()) {
                throw new HeadingCannotBeBlankException();
            }
            if (taskUpdateForAdminsDTO.getHeading().length() < 2 || taskUpdateForAdminsDTO.getHeading().length() > 50) {
                throw new HeadingLengthIsNotSupportedException();
            }
            heading = taskUpdateForAdminsDTO.getHeading();
        }
        String description = null;
        if (taskUpdateForAdminsDTO.getDescription() != null) {
            if (taskUpdateForAdminsDTO.getDescription().isBlank()) {
                throw new DescriptionCannotBeBlankException();
            }
            description = taskUpdateForAdminsDTO.getDescription();
        }
        Priority priority = null;
        if (taskUpdateForAdminsDTO.getPriority() != null) {
            priority = Priority.valueOf(taskUpdateForAdminsDTO.getPriority());
        }

        Task task = new Task(heading, description, priority, comments, implementers);
        Status status = null;
        if (taskUpdateForAdminsDTO.getStatus() != null) {
            status = Status.valueOf(taskUpdateForAdminsDTO.getStatus());
        }
        task.setStatus(status);
        return task;
    }
}
