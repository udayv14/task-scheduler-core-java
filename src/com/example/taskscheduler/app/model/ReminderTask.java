package com.example.taskscheduler.app.model;


import java.time.LocalDateTime;

public class ReminderTask extends Task {

    private final String message;

    public ReminderTask(long taskId,
                        LocalDateTime executeAt,
                        int priority,
                        String message) {
        super(taskId, executeAt, priority);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public void execute() {
        System.out.println(
                "REMINDER TaskId: " + getTaskId() +
                        "  | Time: " + LocalDateTime.now() +
                        " | Message: " + message
        );
    }
}
