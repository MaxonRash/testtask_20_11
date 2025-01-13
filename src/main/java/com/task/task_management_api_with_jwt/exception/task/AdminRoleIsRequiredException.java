package com.task.task_management_api_with_jwt.exception.task;

public class AdminRoleIsRequiredException extends RuntimeException {
    public AdminRoleIsRequiredException() {
        super("You need to have an ADMIN role to access this operation");
    }
}
