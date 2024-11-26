package com.task.testtask_20_11.controller;

import com.task.testtask_20_11.dto.request.TaskCreateDTO;
import com.task.testtask_20_11.dto.request.TaskUpdateForAdminsDTO;
import com.task.testtask_20_11.dto.request.TaskUpdateForUsersDTO;
import com.task.testtask_20_11.dto.response.TaskDTO;
import com.task.testtask_20_11.entity.Task;
import com.task.testtask_20_11.exception.task.AdminRoleIsRequiredException;
import com.task.testtask_20_11.security.entity.Role;
import com.task.testtask_20_11.security.entity.User;
import com.task.testtask_20_11.security.service.UserService;
import com.task.testtask_20_11.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping( value = "/tasks", produces = { MediaType.APPLICATION_JSON_VALUE })
@RequiredArgsConstructor
@Tag(name = "Tasks managing controller")
public class TaskController {
    private final TaskService taskService;
    private final UserService userService;

    @GetMapping
    @Operation(summary = "Get all tasks", description = "Get all tasks with pageable required. Author or one of implementers can be specified. " +
                    "If both are specified returns task with specified author and containing this implementer")
    public Page<TaskDTO> getAllTasks(@ParameterObject Pageable pageable,
                                     @RequestParam(required = false) @Parameter(description = "Author email", example = "max@mail.ru") String author,
                                     @RequestParam(required = false) @Parameter(description = "Implementer email", example = "max@mail.ru") String implementer) {
        Page<TaskDTO> tasks;
        if (author == null && implementer == null) {
            tasks = taskService.findAllTasksByPage(pageable);
        } else if (author != null && implementer == null) {
            tasks = taskService.findAllTasksByPageByAuthor(pageable, author);
        } else if (author == null) {
            tasks = taskService.findAllTasksByPageByImplementer(pageable, implementer);
        } else {
            tasks = taskService.findAllTasksByPageByAuthorAndByImplementer(pageable, author, implementer);
        }
        return tasks;
    }

    @PostMapping("/admin")
    @Operation(summary = "Create task only for users having admin role", description = "Create task")
    @ResponseStatus(HttpStatus.CREATED)
    TaskDTO saveTask(@RequestBody @Valid TaskCreateDTO taskCreateDTO) {
        User currentUser = userService.getCurrentUser();
        // Admin role check instead of @PreAuthorize to respond with custom message
        if (!currentUser.getRole().equals(Role.ROLE_ADMIN)) {
            throw new AdminRoleIsRequiredException();
        }
        Task task = TaskCreateDTO.taskCreateDTOToTaskConverter(taskCreateDTO);
        task.setImplementers(task.getImplementers().stream().map(user -> userService.getByEmail(user.getEmail())).toList());
        task.setComments(task.getComments().stream().peek(comment -> comment.setAuthor(userService.getCurrentUser())).toList());
        var commentsToSaveAfterTask = task.getComments();
        task.setAuthor(currentUser);
        return taskService.createTask(task, commentsToSaveAfterTask);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update task", description = "Can be used by any authorized user if he is specified as one of the implementers. " +
            "Admins can use it even if not specified as one of the implementers. " +
            "Only status can be updated and a new comment can be added in this method. If body is empty but not null ( \"{}\" ) is specified - task with this id is returned")
    @ResponseStatus(HttpStatus.OK)
    TaskDTO updateTask(@RequestBody @Valid TaskUpdateForUsersDTO taskUpdateForUsersDTO, @PathVariable Long id) {
        return taskService.updateTaskForUsers(taskUpdateForUsersDTO, id);
    }

    @PutMapping("/admin/{id}")
    @Operation(summary = "Update task only for users having admin role", description = "Update task or get task by Id if body is empty ( \"{}\" ) " +
    "You can specify any fields. If a field is null or empty, no changes will be done to it")
    @ResponseStatus(HttpStatus.OK)
//    @PreAuthorize("hasRole('ADMIN')") // Can be used to avoid checking admin role further, but no custom message
    TaskDTO updateTask(@RequestBody @Valid TaskUpdateForAdminsDTO taskUpdateForAdminsDTO, @PathVariable Long id) {
        User currentUser = userService.getCurrentUser();
        // Admin role check instead of @PreAuthorize to respond with custom message
        if (!currentUser.getRole().equals(Role.ROLE_ADMIN)) {
            throw new AdminRoleIsRequiredException();
        }
        Task taskToUpdate = TaskUpdateForAdminsDTO.taskUpdateForAdminsDTOToTaskConverter(taskUpdateForAdminsDTO);
        return taskService.updateTaskForAdmins(taskToUpdate, id);
    }

    @DeleteMapping("/admin/{id}")
    @Operation(summary = "Delete task only for users having admin role", description = "Delete task by Id")
    @ResponseStatus(HttpStatus.OK)
    void deleteTask(@PathVariable Long id) {
        User currentUser = userService.getCurrentUser();
        // Admin role check instead of @PreAuthorize to respond with custom message
        if (!currentUser.getRole().equals(Role.ROLE_ADMIN)) {
            throw new AdminRoleIsRequiredException();
        }
        taskService.deleteTask(id);
    }

    @GetMapping("/get-admin")
    @Operation(summary = "Grant ADMIN_ROLE for demonstration", description = "User with token specified in authorization bearer will " +
    "be granted an admin role")
    public void getAdmin() {
        userService.getAdmin();
    }
}