package com.task.testtask_20_11.service;

import com.task.testtask_20_11.dto.request.TaskUpdateForUsersDTO;
import com.task.testtask_20_11.dto.response.TaskDTO;
import com.task.testtask_20_11.entity.Comment;
import com.task.testtask_20_11.entity.Status;
import com.task.testtask_20_11.entity.Task;
import com.task.testtask_20_11.exception.task.NeedToBeAnImplementerException;
import com.task.testtask_20_11.exception.task.TaskNotFoundException;
import com.task.testtask_20_11.repository.TaskRepository;
import com.task.testtask_20_11.security.entity.Role;
import com.task.testtask_20_11.security.entity.User;
import com.task.testtask_20_11.security.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Service for working with {@link Task} from DB
 */
@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final CommentService commentService;
    private final UserService userService;

    /**
     * Find all tasks by {@link Pageable}
     *
     * @param  pageable  {@link Pageable} object
     * @return Page of {@link TaskDTO}
     */
    @Override
    public Page<TaskDTO> findAllTasksByPage(Pageable pageable) {
        Page<Task> tasks = taskRepository.findAll(pageable);
        List<TaskDTO> taskList = tasks.stream().map(TaskDTO::TaskToTaskDTOConverter).toList();
        log.info(String.format("User with email %s executed findAllTasksByPage method", userService.getCurrentUser().getEmail()));
        PageImpl<TaskDTO> dtoPage = new PageImpl<>(taskList, pageable, tasks.getTotalElements());
        return dtoPage;
    }

    /**
     * Find all tasks by {@link Pageable} and email string of the author of a task
     *
     * @param  pageable  {@link Pageable} object
     * @param  authorEmail  email
     * @return Page of {@link TaskDTO}
     */
    @Override
    public Page<TaskDTO> findAllTasksByPageByAuthor(Pageable pageable, String authorEmail) {
        Page<Task> tasks = taskRepository.findTasksByAuthor_Email(pageable, authorEmail);
        List<TaskDTO> taskList = tasks.stream().map(TaskDTO::TaskToTaskDTOConverter).toList();
        log.info(String.format("User with email %s executed findAllTasksByPageByAuthor method", userService.getCurrentUser().getEmail()));
        PageImpl<TaskDTO> dtoPage = new PageImpl<>(taskList, pageable, tasks.getTotalElements());
        return dtoPage;
    }

    /**
     * Find all tasks by {@link Pageable} and string of the implementer email of a task
     *
     * @param  pageable  {@link Pageable} object
     * @param  implementerEmail  email
     * @return Page of {@link TaskDTO}
     */
    @Override
    public Page<TaskDTO> findAllTasksByPageByImplementer(Pageable pageable, String implementerEmail) {
        User user = userService.getByEmail(implementerEmail);
        Page<Task> tasks = taskRepository.findTasksByImplementersContainingIgnoreCase(pageable, user);
        List<TaskDTO> taskList = tasks.stream().map(TaskDTO::TaskToTaskDTOConverter).toList();
        log.info(String.format("User with email %s executed findAllTasksByPageByImplementer method", userService.getCurrentUser().getEmail()));
        PageImpl<TaskDTO> dtoPage = new PageImpl<>(taskList, pageable, tasks.getTotalElements());
        return dtoPage;
    }

    /**
     * Find all tasks by {@link Pageable} and both string of the implementer email and an author email of a task
     *
     * @param  pageable  {@link Pageable} object
     * @param  authorEmail  email
     * @param  implementerEmail  email
     * @return Page of {@link TaskDTO}
     */
    @Override
    public Page<TaskDTO> findAllTasksByPageByAuthorAndByImplementer(Pageable pageable, String authorEmail, String implementerEmail) {
        User implementerFromDB = userService.getByEmail(implementerEmail);
        Page<Task> tasks = taskRepository.findTasksByAuthor_EmailAndImplementersContainingIgnoreCase (pageable, authorEmail, implementerFromDB);
        List<TaskDTO> taskList = tasks.stream().map(TaskDTO::TaskToTaskDTOConverter).toList();
        log.info(String.format("User with email %s executed findAllTasksByPageByAuthorAndByImplementer method", userService.getCurrentUser().getEmail()));
        PageImpl<TaskDTO> dtoPage = new PageImpl<>(taskList, pageable, tasks.getTotalElements());
        return dtoPage;
    }

    /**
     * Find {@link Task} by id or throw {@link TaskNotFoundException}
     *
     * @param  id  id of the task
     * @return {@link TaskDTO}
     * @throws TaskNotFoundException if task is not found in DB
     */
    @Override
    public TaskDTO findById(Long id) {
        Task task = taskRepository.findById(id).orElseThrow(TaskNotFoundException::new);
        return TaskDTO.TaskToTaskDTOConverter(task);
    }

    /**
     * Create new {@link Task} with List<> of {@link Comment} or without it
     *
     * @param  task  {@link Task}
     * @param  comments  List<> of {@link Task}
     * @return {@link TaskDTO}
     */
    @Override
    @Transactional
    public TaskDTO createTask(Task task, List<Comment> comments) {
        Task savedTask = taskRepository.save(task);
        commentService.saveAndAttachCommentsToTask(comments, savedTask);
        Task savedTaskFromDB = taskRepository.findById(savedTask.getId()).orElseThrow(TaskNotFoundException::new);
        log.info(String.format("User with email %s executed createTask method and created Task with Id = %d", userService.getCurrentUser().getEmail(), savedTaskFromDB.getId()));
        TaskDTO taskDTO = TaskDTO.TaskToTaskDTOConverter(savedTaskFromDB);
        return taskDTO;
    }

    /**
     * Update {@link Task} with specified id. Made to be used by users having any {@link Role}
     *
     * @param  taskUpdateForUsersDTO  {@link TaskUpdateForUsersDTO}
     * @param  id  id of the {@link Task}
     * @return {@link TaskDTO}
     * @throws TaskNotFoundException if task is not found in DB
     */
    @Override
    @Transactional
    public TaskDTO updateTaskForUsers(TaskUpdateForUsersDTO taskUpdateForUsersDTO, Long id) {
        Task taskFromDB = taskRepository.findById(id).orElseThrow(TaskNotFoundException::new);
        var implementersEmailsList = taskFromDB.getImplementers().stream().map(User::getEmail).toList();
        if (!(implementersEmailsList.contains(userService.getCurrentUser().getEmail())) && !userService.getCurrentUser().getRole().equals(Role.ROLE_ADMIN)) {
            throw new NeedToBeAnImplementerException();
        }
        List<Comment> newSavedComments = new ArrayList<>();
        if (taskUpdateForUsersDTO.getComments() != null && !taskUpdateForUsersDTO.getComments().isEmpty()) {
            List<Comment> commentsFromUpdateRequest = taskUpdateForUsersDTO.getComments().stream()
                    .map(commentRequestDTO -> new Comment(commentRequestDTO.getText())).toList();
            commentsFromUpdateRequest.forEach(comment -> comment.setAuthor(userService.getCurrentUser()));
            commentsFromUpdateRequest.forEach(comment -> comment.setTask(taskFromDB));
            newSavedComments = commentService.saveAndAttachCommentsToTask(commentsFromUpdateRequest, taskFromDB);
        }
        var comments = taskFromDB.getComments();
        if (!newSavedComments.isEmpty()) {
            comments.addAll(newSavedComments);
            taskFromDB.setComments(comments);
        }
        if (!(taskUpdateForUsersDTO.getStatus() == null) && !taskUpdateForUsersDTO.getStatus().isBlank()) {
            taskFromDB.setStatus(Status.valueOf(taskUpdateForUsersDTO.getStatus()));
        }
        Task updatedTaskFromDB = taskRepository.save(taskFromDB);
        log.info(String.format("User with email %s executed updateTask method and updated Task with Id = %d", userService.getCurrentUser().getEmail(), updatedTaskFromDB.getId()));
        return TaskDTO.TaskToTaskDTOConverter(updatedTaskFromDB);
    }

    /**
     * Update {@link Task} with specified id. Made to be used only by users having {@link Role} ROLE_ADMIN
     *
     * @param  taskToUpdate  {@link Task}
     * @param  id  id of the {@link Task}
     * @return {@link TaskDTO}
     * @throws TaskNotFoundException if task is not found in DB
     */
    @Override
    @Transactional
    public TaskDTO updateTaskForAdmins(Task taskToUpdate, Long id) {
        Task taskFromDB = taskRepository.findById(id).orElseThrow(TaskNotFoundException::new);
        Task clonedTaskFromDB = new Task();
        clonedTaskFromDB.setId(taskFromDB.getId());
        clonedTaskFromDB.setHeading(taskFromDB.getHeading());
        clonedTaskFromDB.setDescription(taskFromDB.getDescription());
        clonedTaskFromDB.setStatus(taskFromDB.getStatus());
        clonedTaskFromDB.setPriority(taskFromDB.getPriority());
        clonedTaskFromDB.setCreatedAt(taskFromDB.getCreatedAt());
        clonedTaskFromDB.setComments(taskFromDB.getComments());
        clonedTaskFromDB.setAuthor(taskFromDB.getAuthor());
        clonedTaskFromDB.setImplementers(taskFromDB.getImplementers());
        if (taskToUpdate.getImplementers() != null) {
            List<User> implementersFromDB = taskToUpdate.getImplementers();
            List<User> usersFromDB = implementersFromDB.stream().map(user -> userService.getByEmail(user.getEmail())).toList();
            clonedTaskFromDB.setImplementers(usersFromDB);
        }
        if (taskToUpdate.getHeading() != null) {
            clonedTaskFromDB.setHeading(taskToUpdate.getHeading());
        }
        if (taskToUpdate.getDescription() != null) {
            clonedTaskFromDB.setDescription(taskToUpdate.getDescription());
        }
        if (taskToUpdate.getStatus() != null) {
            clonedTaskFromDB.setStatus(taskToUpdate.getStatus());
        }
        if (taskToUpdate.getPriority() != null) {
            clonedTaskFromDB.setPriority(taskToUpdate.getPriority());
        }
        Task updatedTask = taskRepository.save(clonedTaskFromDB);
        if (taskToUpdate.getComments() != null) {
            var commentsToAdd = taskToUpdate.getComments().stream()
                    .peek(comment -> comment.setAuthor(userService.getCurrentUser())).toList();
            commentService.saveAndAttachCommentsToTask(commentsToAdd, updatedTask);
        }
        TaskDTO taskToReturn = findById(id);
        log.info(String.format("Admin with email %s executed updateTask method and updated Task with Id = %d", userService.getCurrentUser().getEmail(), taskToReturn.getId()));
        return taskToReturn;
    }

    /**
     * Delete {@link Task} with specified id
     *
     * @param  id  id of the {@link Task}
     */
    @Override
    @Transactional
    public void deleteTask(Long id) {
        findById(id);
        taskRepository.deleteById(id);
        log.info(String.format("Admin with email %s executed deleteTask method and deleted Task with Id = %d", userService.getCurrentUser().getEmail(), id));
    }
}
