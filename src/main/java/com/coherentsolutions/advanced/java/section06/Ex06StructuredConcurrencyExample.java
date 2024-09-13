package com.coherentsolutions.advanced.java.section06;

import java.util.concurrent.StructuredTaskScope;

/**
 * Ex06StructuredConcurrencyExample demonstrates structured concurrency with StructuredTaskScope.
 */
public class Ex06StructuredConcurrencyExample {
    public static void main(String[] args) throws Exception {
        // Use StructuredTaskScope for task management
        try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
            // Fork two tasks
            var task1 = scope.fork(() -> {
                System.out.println("Task 1 running");
                return "Task 1 result";
            });

            var task2 = scope.fork(() -> {
                System.out.println("Task 2 running");
                return "Task 2 result";
            });

            // Wait for all tasks to complete
            scope.join();  // Wait for both tasks to complete or fail
            scope.throwIfFailed();  // Throw an exception if any task failed

            // Get and print the results of both tasks
            System.out.println("Result from task 1: " + task1.get());  // Use get() to retrieve the result
            System.out.println("Result from task 2: " + task2.get());  // Use get() to retrieve the result
        }
    }
}
