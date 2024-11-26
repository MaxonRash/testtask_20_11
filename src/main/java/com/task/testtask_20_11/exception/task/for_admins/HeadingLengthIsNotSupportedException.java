package com.task.testtask_20_11.exception.task.for_admins;

public class HeadingLengthIsNotSupportedException extends RuntimeException {
    public HeadingLengthIsNotSupportedException() {
        super("Heading length must be between 2 and 50 characters");
    }
}
