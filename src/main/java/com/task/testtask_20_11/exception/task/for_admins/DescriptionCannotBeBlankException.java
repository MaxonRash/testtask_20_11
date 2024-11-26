package com.task.testtask_20_11.exception.task.for_admins;

public class DescriptionCannotBeBlankException extends RuntimeException {
    public DescriptionCannotBeBlankException() {
        super("Description cannot be empty");
    }
}
