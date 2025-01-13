package com.task.task_management_api_with_jwt.exception.task.for_admins;

public class DescriptionCannotBeBlankException extends RuntimeException {
    public DescriptionCannotBeBlankException() {
        super("Description cannot be empty");
    }
}
