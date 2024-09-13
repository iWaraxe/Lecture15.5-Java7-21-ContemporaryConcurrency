package com.coherentsolutions.advanced.java.section11;

import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;

/**
 * Ex04VirtualThreadsVsTraditionalThreadPools demonstrates a comparison between
 * traditional thread pools and virtual threads, showing the advantages of scalability
 * and reduced overhead when using virtual threads.
 */
public class Ex04VirtualThreadsVsTraditionalThreadPools {
    public static void main(String[] args) throws InterruptedException {
        // Traditional thread pool model
        ExecutorService threadPool = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 10; i++) {
            threadPool.submit(() -> {
                System.out.println("Task running in traditional thread pool: " + Thread.currentThread().getName());
                try {
                    Thread.sleep(1000);  // Simulate blocking task
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }
        threadPool.shutdown();

        // Virtual thread model
        try (var virtualExecutor = Executors.newVirtualThreadPerTaskExecutor()) {
            for (int i = 0; i < 10; i++) {
                virtualExecutor.submit(() -> {
                    System.out.println("Task running in virtual thread: " + Thread.currentThread().getName());
                    try {
                        Thread.sleep(1000);  // Simulate blocking task
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                });
            }
        }

        System.out.println("Comparison between virtual threads and traditional thread pools complete.");
    }
}
