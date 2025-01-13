package com.task.task_management_api_with_jwt.exception.task.for_admins;

public class ImplementersCannotBeEmptyException extends RuntimeException {
    public ImplementersCannotBeEmptyException() {
        super("You need to specify at least 1 implementer");
    }
}
