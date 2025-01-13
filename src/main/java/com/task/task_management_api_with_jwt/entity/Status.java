package com.task.task_management_api_with_jwt.entity;

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
