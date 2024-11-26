package com.task.testtask_20_11.exception.task;

public class NeedToBeAnImplementerException extends RuntimeException {
    public NeedToBeAnImplementerException() {
        super("You need to be an implementer of this task to access this operation");
    }
}
