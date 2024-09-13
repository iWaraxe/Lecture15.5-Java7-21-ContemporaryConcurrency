package com.coherentsolutions.advanced.java.section04.advanced;

import java.util.concurrent.StructuredTaskScope;
import java.time.Instant;
import java.util.concurrent.TimeUnit;

/**
 * Ex05AdvancedStructuredConcurrencyTimeout demonstrates structured concurrency with timeout handling,
 * ensuring that tasks do not run indefinitely.
 */
public class Ex05AdvancedStructuredConcurrencyTimeout {
    public static void main(String[] args) throws Exception {
        // Using StructuredTaskScope with a timeout to manage tasks
        try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
            // Fork two tasks simulating network calls
            StructuredTaskScope.Subtask<String> task1 = scope.fork(() -> simulateNetworkCall("Task 1", 2000));
            StructuredTaskScope.Subtask<String> task2 = scope.fork(() -> simulateNetworkCall("Task 2", 6000));  // Longer task

            // Wait for tasks with a maximum timeout of 4 seconds
            scope.joinUntil(Instant.now().plusSeconds(4));
            scope.throwIfFailed();

            // Retrieve the results if tasks succeed within the timeout
            String result1 = task1.get();  // Using get() method
            String result2 = task2.get();  // Using get() method

            System.out.println("Both tasks completed. Results: " + result1 + ", " + result2);
        } catch (Exception e) {
            System.err.println("Task timeout or error occurred: " + e.getMessage());
        }
    }

    // Simulate a network call with a delay
    private static String simulateNetworkCall(String taskName, int delay) throws InterruptedException {
        Thread.sleep(delay);  // Simulate network latency
        return taskName + " result";
    }
}
