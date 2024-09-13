package com.coherentsolutions.advanced.java.section04.advanced;

import java.util.concurrent.Executors;
import java.util.stream.IntStream;

/**
 * Ex04AdvancedVirtualThreadErrorHandling demonstrates error handling in virtual threads
 * using try-catch blocks to ensure the application continues running smoothly.
 */
public class Ex04AdvancedVirtualThreadErrorHandling {
    public static void main(String[] args) {
        // Create a virtual thread-per-task executor
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            // Submit 1,000 tasks with one task deliberately throwing an exception
            IntStream.range(0, 1000).forEach(i -> {
                executor.submit(() -> {
                    if (i == 500) {
                        throw new RuntimeException("Error in Task 500");
                    }
                    System.out.println("Task " + i + " completed successfully in thread: " + Thread.currentThread().getName());
                });
            });
        } catch (Exception e) {
            System.err.println("Exception encountered: " + e.getMessage());
        }

        System.out.println("Main thread completed.");
    }
}
