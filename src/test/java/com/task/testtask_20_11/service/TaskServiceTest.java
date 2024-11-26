package com.task.testtask_20_11.service;

import com.task.testtask_20_11.entity.Priority;
import com.task.testtask_20_11.entity.Status;
import com.task.testtask_20_11.entity.Task;
import com.task.testtask_20_11.repository.TaskRepository;
import com.task.testtask_20_11.security.entity.Role;
import com.task.testtask_20_11.security.entity.User;
import com.task.testtask_20_11.security.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@DisplayName("Task service testing")
public class TaskServiceTest {
    private TaskService taskService;
    private TaskRepository taskRepository;
    private User mockCurrentUser;
    private Task mockTaskFromDB;

    @BeforeEach
    public void init() {
        CommentService commentService = Mockito.mock(CommentService.class);
        UserService userService = Mockito.mock(UserService.class);
        taskRepository = Mockito.mock(TaskRepository.class);
        taskService = new TaskServiceImpl(taskRepository, commentService, userService);

        mockCurrentUser = new User();
        mockCurrentUser.setId(1L);
        mockCurrentUser.setRole(Role.ROLE_USER);
        mockCurrentUser.setEmail("max@mail.ru");

        mockTaskFromDB = new Task();
        mockTaskFromDB.setId(1L);
        mockTaskFromDB.setStatus(Status.WAITING);
        mockTaskFromDB.setCreatedAt(Calendar.getInstance());
        mockTaskFromDB.setAuthor(mockCurrentUser);
        mockTaskFromDB.setPriority(Priority.LOW);
        mockTaskFromDB.setHeading("Test heading");
        mockTaskFromDB.setDescription("Test description");
        mockTaskFromDB.setComments(new ArrayList<>());
        mockTaskFromDB.setImplementers(List.of(mockCurrentUser));


        Mockito.when(taskRepository.findById(1L)).thenReturn(Optional.of(mockTaskFromDB));
        Mockito.when(userService.getCurrentUser()).thenReturn(mockCurrentUser);
    }

    @Test
    public void shouldProperlySaveTask() {
        //given
        Task task = new Task("Test heading", "Test description", Priority.LOW, new ArrayList<>(), List.of(mockCurrentUser));
        task.setAuthor(mockCurrentUser);

        Mockito.when(taskRepository.save(task)).thenReturn(mockTaskFromDB);

        // when
        taskService.createTask(task, new ArrayList<>());

        //then
        Mockito.verify(taskRepository).save(task);
    }
}
