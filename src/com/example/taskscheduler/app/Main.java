package com.example.taskscheduler.app;
import com.example.taskscheduler.app.model.ReminderTask;
import com.example.taskscheduler.app.queue.TaskQueue;
import com.example.taskscheduler.app.scheduler.TaskScheduler;
import com.example.taskscheduler.app.scheduler.WorkerThread;

import java.time.LocalDateTime;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        TaskQueue taskQueue = new TaskQueue();
        TaskScheduler scheduler = new TaskScheduler(taskQueue);

        WorkerThread worker = new WorkerThread(taskQueue);
        Thread workerThread = new Thread(worker, "task-worker");
        workerThread.start();

        Scanner scanner = new Scanner(System.in);
        long taskIdCounter = 1;

        System.out.println("=== TASK SCHEDULER STARTED ===");

        while (true) {
            System.out.println("\nChoose an option:");
            System.out.println("1. Add Reminder Task");
            System.out.println("2. List Pending Tasks");
            System.out.println("3. List Completed Tasks");
            System.out.println("4. Cancel Task by ID");
            System.out.println("5. Exit");
            System.out.println("6. List Cancelled Tasks");
            System.out.print("Enter choice: ");


            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {

                case 1 -> {
                    System.out.print("Enter Reminder Message: ");
                    String message = scanner.nextLine();

                    if (message.trim().isEmpty() || message.matches("\\d+")) {
                        System.out.println(" Invalid message. Enter Meaningful Text.");
                        continue;
                    }

                    System.out.print("Execute After How Many Minutes?: ");
                    int delayMinutes = scanner.nextInt();

                    System.out.print("Enter Priority (higher number = higher priority): ");
                    int priority = scanner.nextInt();

                    LocalDateTime executeAt =
                            LocalDateTime.now().plusMinutes(delayMinutes);

                    scheduler.scheduleTask(
                            new ReminderTask(
                                    taskIdCounter++,
                                    executeAt,
                                    priority,
                                    message
                            )
                    );

                    System.out.println(" Task scheduled.");
                }

                    case 2 -> taskQueue.printPendingTasks();

                    case 3 -> taskQueue.printCompletedTasks();

                    case 4 -> {
                        System.out.print("Enter task ID to cancel: ");
                        long id = scanner.nextLong();

                        boolean cancelled = taskQueue.cancelTaskById(id);
                        if (cancelled) {
                            System.out.println(" Task cancelled.");
                        } else {
                            System.out.println(" Task not found or already executed.");
                        }
                    }

                    case 5 -> taskQueue.printCancelledTasks();
                case 6 -> taskQueue.printCancelledTasks();
                    case 7 -> {
                        System.out.println("Shutting down scheduler...");
                        worker.stopWorker();
                        workerThread.join();
                        System.out.println("Application exited safely.");
                        return;
                    }

                    default -> System.out.println(" Invalid option.");
                }


            }
    }
}


