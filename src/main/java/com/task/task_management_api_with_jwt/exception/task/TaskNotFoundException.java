package com.task.task_management_api_with_jwt.exception.task;

public class TaskNotFoundException extends RuntimeException {
    public TaskNotFoundException() {
        super("Task with this id is not found");
    }
}
