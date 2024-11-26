package com.task.testtask_20_11.dto.request;

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
@Schema(description = "Task update request: status can be updated and a new comment can be added")
public class TaskUpdateForUsersDTO {
    @Schema(description = "Status", examples = {"WAITING", "IN_PROGRESS", "DONE"})
    @Pattern(regexp = "WAITING|IN_PROGRESS|DONE", message = "Allowed values are: WAITING, IN_PROGRESS, DONE")
    private String status;

    @Parameter(description = "Add new comment to this task")
    @Nullable
    @Valid
    private List<CommentRequestDTO> comments;

}
