package com.coherentsolutions.advanced.java.section04;

import java.util.concurrent.Executors;
import java.util.stream.IntStream;

/**
 * Ex03VirtualThreadsScalabilityDemo demonstrates the scalability of virtual threads
 * by running thousands of tasks with minimal resource usage.
 */
public class Ex03VirtualThreadsScalabilityDemo {
    public static void main(String[] args) {
        // Create an executor for managing virtual threads
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            // Submit 10,000 virtual threads, each simulating a short task
            IntStream.range(0, 10_000_000).forEach(i -> {
                executor.submit(() -> {
                    try {
                        Thread.sleep(10);  // Simulate a small task with sleep
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println("Task " + i + " executed by: " + Thread.currentThread().getName());
                });
            });
        }

        System.out.println("Main thread completed.");
    }
}
