package com.task.testtask_20_11.service;

import com.task.testtask_20_11.dto.request.TaskUpdateForUsersDTO;
import com.task.testtask_20_11.dto.response.TaskDTO;
import com.task.testtask_20_11.entity.Comment;
import com.task.testtask_20_11.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TaskService {
    Page<TaskDTO> findAllTasksByPage(Pageable pageable);
    Page<TaskDTO> findAllTasksByPageByAuthor(Pageable pageable, String authorEmail);
    Page<TaskDTO> findAllTasksByPageByImplementer(Pageable pageable, String implementerEmail);
    Page<TaskDTO> findAllTasksByPageByAuthorAndByImplementer(Pageable pageable, String authorEmail, String implementerEmail);
    TaskDTO findById(Long id);
    TaskDTO createTask(Task task, List<Comment> comments);
    TaskDTO updateTaskForUsers(TaskUpdateForUsersDTO taskUpdateForUsersDTO, Long id);
    TaskDTO updateTaskForAdmins(Task taskToUpdate, Long id);
    void deleteTask(Long id);
}
