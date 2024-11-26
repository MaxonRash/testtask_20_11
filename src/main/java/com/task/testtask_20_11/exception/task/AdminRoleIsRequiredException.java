package com.task.testtask_20_11.exception.task;

public class AdminRoleIsRequiredException extends RuntimeException {
    public AdminRoleIsRequiredException() {
        super("You need to have an ADMIN role to access this operation");
    }
}
