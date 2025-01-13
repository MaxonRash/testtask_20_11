package com.task.task_management_api_with_jwt.exception.task.for_admins;

public class HeadingLengthIsNotSupportedException extends RuntimeException {
    public HeadingLengthIsNotSupportedException() {
        super("Heading length must be between 2 and 50 characters");
    }
}
