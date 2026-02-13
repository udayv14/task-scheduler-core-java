package com.example.taskscheduler.app.queue;
import com.example.taskscheduler.app.status.TaskStatus;
import java.util.Iterator;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import com.example.taskscheduler.app.model.Task;
import com.example.taskscheduler.app.util.TaskComparator;

public class TaskQueue {

    private final PriorityQueue<Task> taskQueue;
    private final List<Task> completedTasks = new ArrayList<>();

    public TaskQueue() {
        this.taskQueue = new PriorityQueue<>(new TaskComparator());
    }

    // add new task
    public synchronized void addTask(Task task) {
        taskQueue.offer(task);
    }

    // remove next task
    public synchronized Task pollTask() {
        return taskQueue.poll();
    }

    // peek next task
    public synchronized Task peekTask() {
        return taskQueue.peek();
    }

    public synchronized boolean isEmpty() {
        return taskQueue.isEmpty();
    }

    // add completed / cancelled / failed task to history
    public synchronized void addCompletedTask(Task task) {
        completedTasks.add(task);
    }

    // list pending tasks
    public synchronized void printPendingTasks() {
        if (taskQueue.isEmpty()) {
            System.out.println("No pending tasks.");
            return;
        }

        System.out.println("\n Pending Tasks. ");
        for (Task task : taskQueue) {
            System.out.println(
                    "ID: " + task.getTaskId() +
                            " | Time: " + task.getExecuteAt() +
                            " | Priority: " + task.getPriority() +
                            " | Status: " + task.getStatus()

            );
        }
    }

    // cancel task by ID
    public synchronized boolean cancelTaskById(long taskId) {

        Iterator<Task> iterator = taskQueue.iterator();

        while (iterator.hasNext()) {
            Task task = iterator.next();

            if (task.getTaskId() == taskId) {
                task.setStatus(TaskStatus.CANCELLED);
                iterator.remove();        // safe removal
                completedTasks.add(task);
                return true;
            }
        }
        return false;
    }
    public synchronized void printCompletedTasks() {
        boolean found = false;
        System.out.println("\n Completed Tasks ");

        for (Task task : completedTasks) {
            if (task.getStatus() == TaskStatus.COMPLETED) {
                found = true;
                System.out.println(
                        "ID: " + task.getTaskId() +
                                " | Time: " + task.getExecuteAt() +
                                " | Priority: " + task.getPriority() +
                                " | Status: " + task.getStatus()

                );
            }
        }

        if (!found) {
            System.out.println("No Completed tasks yet.");
        }
    }


    public synchronized void printCancelledTasks() {
        boolean found = false;
        System.out.println("\n  Cancelled Tasks.  ");

        for (Task task : completedTasks) {
            if (task.getStatus() == TaskStatus.CANCELLED) {
                found = true;
                System.out.println(
                        "ID: " + task.getTaskId() +
                                " | Time: " + task.getExecuteAt() +
                                " | Priority: " + task.getPriority() +
                                " | Status: " + task.getStatus()

                );
            }
        }


        if (!found) {
            System.out.println("No Cancelled tasks.");
        }
    }
}