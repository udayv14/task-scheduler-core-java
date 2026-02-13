package com.example.taskscheduler.app.scheduler;


import com.example.taskscheduler.app.model.Task;
import com.example.taskscheduler.app.queue.TaskQueue;

import java.time.LocalDateTime;

public class TaskScheduler {

    private final TaskQueue taskQueue;

    public TaskScheduler(TaskQueue taskQueue) {
        this.taskQueue = taskQueue;
    }


    public void scheduleTask(Task task) {


        if (task.getExecuteAt().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException(
                    "Task execution time cannot be in the past"
            );
        }

        taskQueue.addTask(task);
    }

    public TaskQueue getTaskQueue() {
        return taskQueue;
    }
}

