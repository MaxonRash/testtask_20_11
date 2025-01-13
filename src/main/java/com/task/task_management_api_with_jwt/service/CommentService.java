package com.task.task_management_api_with_jwt.service;

import com.task.task_management_api_with_jwt.entity.Comment;
import com.task.task_management_api_with_jwt.entity.Task;

import java.util.List;

public interface CommentService {
    List<Comment> saveAndAttachCommentsToTask(List<Comment> comments, Task task);
}
