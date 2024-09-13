package com.coherentsolutions.advanced.java.section07;

import java.util.concurrent.Executors;

/**
 * Ex04OveruseOfThreadsExample demonstrates the difference between platform threads
 * and virtual threads in terms of scalability and resource usage.
 */
public class Ex04OveruseOfThreadsExample {
    public static void main(String[] args) throws InterruptedException {
        // Using traditional platform threads (fixed thread pool)
        try (var platformExecutor = Executors.newFixedThreadPool(100)) {
            for (int i = 0; i < 100; i++) {
                platformExecutor.submit(() -> {
                    System.out.println("Task running in platform thread: " + Thread.currentThread().getName());
                });
            }
        }

        // Using virtual threads (introduced in Project Loom)
        try (var virtualExecutor = Executors.newVirtualThreadPerTaskExecutor()) {
            for (int i = 0; i < 1000; i++) {
                virtualExecutor.submit(() -> {
                    System.out.println("Task running in virtual thread: " + Thread.currentThread().getName());
                });
            }
        }

        System.out.println("Main thread completed.");
    }
}
