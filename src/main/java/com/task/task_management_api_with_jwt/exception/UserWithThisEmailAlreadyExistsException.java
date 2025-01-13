package com.task.task_management_api_with_jwt.exception;

public class UserWithThisEmailAlreadyExistsException extends RuntimeException {
    public UserWithThisEmailAlreadyExistsException() {
        super("User with this Email already exists");
    }
}
