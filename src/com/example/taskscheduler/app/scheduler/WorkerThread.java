package com.example.taskscheduler.app.scheduler;

import com.example.taskscheduler.app.model.Task;
import com.example.taskscheduler.app.queue.TaskQueue;
import com.example.taskscheduler.app.status.TaskStatus;

import java.time.LocalDateTime;
public class WorkerThread implements Runnable {

    private final TaskQueue taskQueue;
    private volatile boolean running = true;

    public WorkerThread(TaskQueue taskQueue) {
        this.taskQueue = taskQueue;
    }

    // graceful shutdown support
    public void stopWorker() {
        running = false;
    }

    @Override
    public void run() {

        while (running) {
            try {
                Task task = taskQueue.peekTask();

                if (task == null) {
                    Thread.sleep(500);
                    continue;
                }

                // check execution time
                if (!task.getExecuteAt().isAfter(LocalDateTime.now())) {

                    taskQueue.pollTask(); // remove from pending
                    task.setStatus(TaskStatus.RUNNING);

                    try {
                        task.execute();
                        task.setStatus(TaskStatus.COMPLETED);
                    } catch (Exception ex) {
                        task.setStatus(TaskStatus.FAILED);
                        System.out.println(
                                " Task failed. ID: " + task.getTaskId()
                        );
                    }


                    taskQueue.addCompletedTask(task);

                } else {
                    Thread.sleep(500);
                }

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }

        System.out.println("Worker thread stopped gracefully.");
    }
}




