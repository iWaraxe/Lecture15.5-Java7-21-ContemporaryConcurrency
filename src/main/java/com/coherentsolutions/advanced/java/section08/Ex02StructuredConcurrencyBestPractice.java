package com.coherentsolutions.advanced.java.section08;

import java.util.concurrent.StructuredTaskScope;

/**
 * Ex02StructuredConcurrencyBestPractice demonstrates the use of structured concurrency to manage grouped tasks
 * and handle exceptions.
 */
public class Ex02StructuredConcurrencyBestPractice {
    public static void main(String[] args) throws Exception {
        // Using Structured Concurrency to manage multiple tasks
        try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
            var task1 = scope.fork(() -> {
                System.out.println("Task 1 running");
                Thread.sleep(1000);
                return "Task 1 result";
            });

            var task2 = scope.fork(() -> {
                System.out.println("Task 2 running");
                Thread.sleep(1500);
                return "Task 2 result";
            });

            scope.join();  // Wait for all tasks to complete or fail
            scope.throwIfFailed();  // Throw an exception if any task fails

            // If both tasks succeed, print their results
            System.out.println("Result from task 1: " + task1.get());
            System.out.println("Result from task 2: " + task2.get());
        }
    }
}
