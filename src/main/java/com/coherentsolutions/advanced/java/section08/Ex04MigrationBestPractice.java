package com.coherentsolutions.advanced.java.section08;

import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;

/**
 * Ex04MigrationBestPractice demonstrates the migration from traditional thread pools to virtual threads in a gradual manner.
 */
public class Ex04MigrationBestPractice {
    public static void main(String[] args) throws InterruptedException {
        // Traditional thread pool model
        ExecutorService traditionalPool = Executors.newFixedThreadPool(4);
        for (int i = 0; i < 100; i++) {
            traditionalPool.submit(() -> {
                System.out.println("Task running in traditional thread pool: " + Thread.currentThread().getName());
            });
        }
        traditionalPool.shutdown();

        // New virtual thread model (migrating to virtual threads)
        try (var virtualExecutor = Executors.newVirtualThreadPerTaskExecutor()) {
            for (int i = 0; i < 100; i++) {
                virtualExecutor.submit(() -> {
                    System.out.println("Task running in virtual thread: " + Thread.currentThread().getName());
                });
            }
        }

        System.out.println("Main thread completed.");
    }
}
