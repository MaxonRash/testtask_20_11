package com.task.testtask_20_11.entity;

public enum Status {
    WAITING("В ожидании"), IN_PROGRESS("В процессе"), DONE("Завершено");
    private final String text;
    Status(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
