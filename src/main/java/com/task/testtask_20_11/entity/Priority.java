package com.task.testtask_20_11.entity;

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
