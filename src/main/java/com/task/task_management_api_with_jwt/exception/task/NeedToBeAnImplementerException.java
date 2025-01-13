package com.task.task_management_api_with_jwt.exception.task;

public class NeedToBeAnImplementerException extends RuntimeException {
    public NeedToBeAnImplementerException() {
        super("You need to be an implementer of this task to access this operation");
    }
}
