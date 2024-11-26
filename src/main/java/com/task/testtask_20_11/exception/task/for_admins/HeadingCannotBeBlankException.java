package com.task.testtask_20_11.exception.task.for_admins;

public class HeadingCannotBeBlankException extends RuntimeException {
    public HeadingCannotBeBlankException() {
        super("Heading cannot be empty");
    }
}
