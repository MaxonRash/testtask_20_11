package com.task.testtask_20_11.exception;

public class UserWithThisEmailAlreadyExistsException extends RuntimeException {
    public UserWithThisEmailAlreadyExistsException() {
        super("User with this Email already exists");
    }
}
