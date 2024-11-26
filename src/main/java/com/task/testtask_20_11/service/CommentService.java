package com.task.testtask_20_11.service;

import com.task.testtask_20_11.entity.Comment;
import com.task.testtask_20_11.entity.Task;

import java.util.List;

public interface CommentService {
    List<Comment> saveAndAttachCommentsToTask(List<Comment> comments, Task task);
}
