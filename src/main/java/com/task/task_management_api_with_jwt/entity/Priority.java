package com.task.task_management_api_with_jwt.entity;

public enum Priority {
    HIGH("Высокий"), AVERAGE("Средний"), LOW("Низкий");
    private final String text;
    Priority(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
