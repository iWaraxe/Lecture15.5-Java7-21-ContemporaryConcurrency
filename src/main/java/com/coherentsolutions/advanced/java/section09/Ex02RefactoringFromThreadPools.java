package com.coherentsolutions.advanced.java.section09;

import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;

/**
 * Ex02RefactoringFromThreadPools demonstrates how to replace traditional thread pools with virtual threads.
 */
public class Ex02RefactoringFromThreadPools {
    public static void main(String[] args) throws InterruptedException {
        // Traditional thread pool
        ExecutorService traditionalPool = Executors.newFixedThreadPool(4);
        for (int i = 0; i < 10; i++) {
            traditionalPool.submit(() -> {
                System.out.println("Running in traditional thread pool: " + Thread.currentThread().getName());
            });
        }
        traditionalPool.shutdown();

        // Virtual threads (new refactored approach)
        try (var virtualExecutor = Executors.newVirtualThreadPerTaskExecutor()) {
            for (int i = 0; i < 10; i++) {
                virtualExecutor.submit(() -> {
                    System.out.println("Running in virtual thread: " + Thread.currentThread().getName());
                });
            }
        }

        System.out.println("Tasks completed using virtual threads.");
    }
}
