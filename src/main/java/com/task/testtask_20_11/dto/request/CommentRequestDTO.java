package com.task.testtask_20_11.dto.request;

import com.task.testtask_20_11.entity.Comment;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Comment creation request")
public class CommentRequestDTO {
    @Schema(description = "Text of the comment", example = "This task should be done in 2 days")
    @NotBlank(message = "Text of the comment cannot be empty")
    private String text;

    /**
     * Returns new {@link Comment} with set fields: text, createdAt
     * @param commentRequestDTO
     * @return {@link Comment}
     */
    public static Comment commentRequestDTOToCommentConverter(CommentRequestDTO commentRequestDTO) {
        Comment newComment = new Comment(commentRequestDTO.getText());
        return newComment;
    }
}
