package com.coherentsolutions.advanced.java.section06;

import java.util.concurrent.Executors;

/**
 * Ex04SimplifiedWithVirtualThreads demonstrates how virtual threads simplify asynchronous code.
 */
public class Ex04SimplifiedWithVirtualThreads {
    public static void main(String[] args) throws Exception {
        // Create a virtual thread executor
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            // Submit a task to the virtual thread executor
            executor.submit(() -> {
                System.out.println("Virtual thread running: " + Thread.currentThread().getName());
                // Simulate a non-blocking task using Thread.sleep
                try {
                    Thread.sleep(1000);  // Non-blocking with virtual threads
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("Task completed.");
            }).get();  // Block until the task is completed
        }

        System.out.println("Main thread completed.");
    }
}
