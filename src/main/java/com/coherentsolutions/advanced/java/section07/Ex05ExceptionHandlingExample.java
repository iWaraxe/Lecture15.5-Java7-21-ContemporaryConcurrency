package com.coherentsolutions.advanced.java.section07;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Ex05ExceptionHandlingExample demonstrates how to handle exceptions in concurrent code
 * by properly catching exceptions in threads and using Future.get().
 */
public class Ex05ExceptionHandlingExample {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(2);

        // Submit a task that throws an exception
        Future<?> future = executor.submit(() -> {
            throw new RuntimeException("Error in thread");
        });

        // Handle exceptions from the Future
        try {
            future.get();  // Block until the task completes
        } catch (Exception e) {
            System.err.println("Exception caught from thread: " + e.getMessage());
        }

        // Submit a task with exception handling inside the thread
        executor.submit(() -> {
            try {
                System.out.println("Task running in thread: " + Thread.currentThread().getName());
                throw new RuntimeException("Exception inside the thread");
            } catch (Exception e) {
                System.err.println("Exception caught inside thread: " + e.getMessage());
            }
        });

        executor.shutdown();
    }
}
