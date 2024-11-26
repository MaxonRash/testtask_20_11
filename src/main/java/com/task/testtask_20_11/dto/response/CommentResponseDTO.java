package com.task.testtask_20_11.dto.response;

import com.task.testtask_20_11.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.SimpleDateFormat;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentResponseDTO {
    private Long id;
    private String text;
    private String createdAt;
    private String author;

    public static CommentResponseDTO commentToCommentDTOConverter(Comment comment) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        CommentResponseDTO commentDTO = CommentResponseDTO.builder().id(comment.getId()).text(comment.getText())
                .createdAt(sdf.format(comment.getCreatedAt().getTime())).author(comment.getAuthor().getEmail()).build();
        return commentDTO;
    }
}
