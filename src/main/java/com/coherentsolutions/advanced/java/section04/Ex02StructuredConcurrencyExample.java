package com.coherentsolutions.advanced.java.section04;

import java.util.concurrent.StructuredTaskScope;

/**
 * Ex02StructuredConcurrencyExample demonstrates the use of StructuredTaskScope
 * for grouping multiple tasks and managing them as a single unit of work.
 */
public class Ex02StructuredConcurrencyExample {
    public static void main(String[] args) throws Exception {
        // Using StructuredTaskScope for task management
        try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
            // Fork two tasks simulating network calls
            StructuredTaskScope.Subtask<String> task1 = scope.fork(() -> simulateNetworkCall("Task 1", 2000));
            StructuredTaskScope.Subtask<String> task2 = scope.fork(() -> simulateNetworkCall("Task 2", 3000));

            // Wait for both tasks to complete or fail
            scope.join().throwIfFailed();

            // If both tasks succeed, retrieve their results using get()
            String result1 = task1.get();  // Use get() to retrieve the result
            String result2 = task2.get();  // Use get() to retrieve the result

            System.out.println("Both tasks completed. Results: " + result1 + ", " + result2);
        } catch (Exception e) {
            System.err.println("Task failed or was interrupted: " + e.getMessage());
        }
    }

    // Simulate a network call with a delay to represent an I/O-bound task
    private static String simulateNetworkCall(String taskName, int delay) throws InterruptedException {
        Thread.sleep(delay);  // Simulating network latency
        return taskName + " result";
    }
}
