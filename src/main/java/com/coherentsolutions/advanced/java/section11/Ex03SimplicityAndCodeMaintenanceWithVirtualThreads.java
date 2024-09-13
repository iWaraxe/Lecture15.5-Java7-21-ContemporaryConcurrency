package com.coherentsolutions.advanced.java.section11;

import java.util.concurrent.Executors;

/**
 * Ex03SimplicityAndCodeMaintenanceWithVirtualThreads demonstrates how virtual threads
 * simplify concurrency by allowing synchronous-looking code to be used, reducing the need
 * for complex asynchronous patterns while improving scalability.
 */
public class Ex03SimplicityAndCodeMaintenanceWithVirtualThreads {
    public static void main(String[] args) {
        // Simplifying concurrency by using virtual threads for a thread-per-task model
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            for (int i = 0; i < 10; i++) {
                executor.submit(() -> {
                    System.out.println("Task handled by virtual thread: " + Thread.currentThread().getName());
                    // Simulate some blocking operation, such as I/O
                    try {
                        Thread.sleep(500);  // Simulates a blocking task
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                });
            }
        }

        System.out.println("All tasks executed with virtual threads.");
    }
}
