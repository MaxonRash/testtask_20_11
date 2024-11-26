package com.task.testtask_20_11.exception.task;

public class TaskNotFoundException extends RuntimeException {
    public TaskNotFoundException() {
        super("Task with this id is not found");
    }
}
