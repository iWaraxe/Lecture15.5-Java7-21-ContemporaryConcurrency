package com.coherentsolutions.advanced.java.section04;

import java.time.Duration;
import java.util.stream.IntStream;
import java.util.concurrent.Executors;

/**
 * Ex01VirtualThreadsExample demonstrates the creation and management of virtual threads
 * using the newVirtualThreadPerTaskExecutor from Project Loom.
 */
public class Ex01VirtualThreadsExample {
    public static void main(String[] args) {
        // Create a virtual thread-per-task executor
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            // Submit 1,000 tasks, each running on a virtual thread
            IntStream.range(0, 1000).forEach(i -> {
                executor.submit(() -> {
                    // Each task sleeps for 100 milliseconds and prints its task number
                    try {
                        Thread.sleep(Duration.ofMillis(100));
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println("Task " + i + " completed in thread: " + Thread.currentThread().getName());
                });
            });
        }

        System.out.println(Thread.currentThread().getName() + "Main thread completed.");
    }
}
