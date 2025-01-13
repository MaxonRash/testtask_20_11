package com.task.task_management_api_with_jwt.exception;

public class UserWithThisEmailNotFoundException extends RuntimeException {
    public UserWithThisEmailNotFoundException() {
        super("User with this email is not found");
    }
}
