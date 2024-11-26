package com.task.testtask_20_11.exception.task.for_admins;

public class ImplementersCannotBeEmptyException extends RuntimeException {
    public ImplementersCannotBeEmptyException() {
        super("You need to specify at least 1 implementer");
    }
}
