package com.coherentsolutions.advanced.java.section09;

import java.util.concurrent.Executors;

/**
 * Ex01TransitionToVirtualThreads demonstrates how to migrate a traditional thread-based task to virtual threads.
 */
public class Ex01TransitionToVirtualThreads {
    public static void main(String[] args) {
        // Traditional thread-based task (using thread pools or platform threads)
        Runnable traditionalTask = () -> {
            System.out.println("Running task in traditional thread: " + Thread.currentThread().getName());
            try {
                Thread.sleep(1000); // Simulate blocking I/O
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        };

        // Migrating to virtual threads
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            for (int i = 0; i < 10; i++) {
                executor.submit(traditionalTask);  // Submit the task to the virtual thread executor
            }
        }

        System.out.println("All tasks submitted to virtual threads.");
    }
}
