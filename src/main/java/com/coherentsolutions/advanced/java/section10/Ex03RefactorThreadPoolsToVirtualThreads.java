package com.coherentsolutions.advanced.java.section10;

import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;

/**
 * Ex03RefactorThreadPoolsToVirtualThreads demonstrates how to refactor an application
 * using traditional thread pools to virtual threads for improved scalability and simplicity.
 */
public class Ex03RefactorThreadPoolsToVirtualThreads {
    public static void main(String[] args) {
        // Legacy application using traditional thread pool
        ExecutorService threadPool = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 10; i++) {
            threadPool.submit(() -> {
                System.out.println("Task running in traditional thread pool: " + Thread.currentThread().getName());
                try {
                    Thread.sleep(1000);  // Simulate blocking operation
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }
        threadPool.shutdown();

        // Refactored to use virtual threads
        try (var virtualExecutor = Executors.newVirtualThreadPerTaskExecutor()) {
            for (int i = 0; i < 10; i++) {
                virtualExecutor.submit(() -> {
                    System.out.println("Task running in virtual thread: " + Thread.currentThread().getName());
                    try {
                        Thread.sleep(1000);  // Simulate blocking operation
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                });
            }
        }

        System.out.println("Refactoring complete. All tasks submitted.");
    }
}
