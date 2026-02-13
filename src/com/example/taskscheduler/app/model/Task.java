package com.example.taskscheduler.app.model;

import com.example.taskscheduler.app.status.TaskStatus;

import java.time.LocalDateTime;

public abstract class Task {

    private final long taskId;
    private final LocalDateTime executeAt;
    private final int priority;

    private TaskStatus status;

    public Task(long taskId, LocalDateTime executeAt, int priority) {
        this.taskId = taskId;
        this.executeAt = executeAt;
        this.priority = priority;
        this.status = TaskStatus.PENDING;
    }

    public long getTaskId() {
        return taskId;
    }

    public LocalDateTime getExecuteAt() {
        return executeAt;
    }

    public int getPriority() {
        return priority;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public abstract void execute();
}
