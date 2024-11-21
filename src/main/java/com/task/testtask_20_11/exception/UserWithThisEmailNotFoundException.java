package com.task.testtask_20_11.exception;

public class UserWithThisEmailNotFoundException extends RuntimeException {
    public UserWithThisEmailNotFoundException() {
        super("User with this email is not found");
    }
}
