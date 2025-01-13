package com.task.task_management_api_with_jwt.service;

import com.task.task_management_api_with_jwt.entity.Comment;
import com.task.task_management_api_with_jwt.entity.Task;
import com.task.task_management_api_with_jwt.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service for working with {@link Comment} from DB
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;

    /**
     * Save and attach comments to the task
     *
     * @param comments  List<> of comments
     * @param task      {@link Task} to which specified comments are needed to be attached
     * @return saved List<> of {@link Comment}
     */
    @Override
    @Transactional
    public List<Comment> saveAndAttachCommentsToTask(List<Comment> comments, Task task) {
        comments.forEach(comment -> comment.setTask(task));
        return commentRepository.saveAll(comments);


    }
}
