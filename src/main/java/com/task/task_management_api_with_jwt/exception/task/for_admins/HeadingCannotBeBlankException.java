package com.task.task_management_api_with_jwt.exception.task.for_admins;

public class HeadingCannotBeBlankException extends RuntimeException {
    public HeadingCannotBeBlankException() {
        super("Heading cannot be empty");
    }
}
