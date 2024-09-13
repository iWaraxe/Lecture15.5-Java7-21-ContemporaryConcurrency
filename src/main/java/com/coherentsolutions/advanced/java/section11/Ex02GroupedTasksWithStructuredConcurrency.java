package com.coherentsolutions.advanced.java.section11;

import java.util.concurrent.StructuredTaskScope;

/**
 * Ex02GroupedTasksWithStructuredConcurrency demonstrates using structured concurrency
 * to manage a group of related tasks that are executed concurrently.
 * If any task fails, the rest are canceled, ensuring consistent task management.
 */
public class Ex02GroupedTasksWithStructuredConcurrency {
    public static void main(String[] args) throws Exception {
        // Using structured concurrency to manage multiple related tasks
        try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
            var task1 = scope.fork(() -> simulateTask("Task 1", 1000));
            var task2 = scope.fork(() -> simulateTask("Task 2", 1500));
            var task3 = scope.fork(() -> simulateTask("Task 3", 2000));

            // Wait for all tasks to complete or fail
            scope.join();
            scope.throwIfFailed();

            // If all tasks succeed, print their results
            System.out.println("All tasks completed successfully:");
            System.out.println("Result from task 1: " + task1.get());
            System.out.println("Result from task 2: " + task2.get());
            System.out.println("Result from task 3: " + task3.get());
        }
    }

    // Simulates a task with a delay, returning a result after the delay
    private static String simulateTask(String taskName, int delay) throws InterruptedException {
        Thread.sleep(delay);  // Simulate a task delay
        System.out.println(taskName + " completed.");
        return taskName + " result";
    }
}
