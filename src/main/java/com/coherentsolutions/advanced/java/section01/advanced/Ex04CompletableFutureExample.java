package com.coherentsolutions.advanced.java.section01.advanced;

import java.util.concurrent.CompletableFuture;

/**
 * Ex04CompletableFutureExample demonstrates the use of CompletableFuture for asynchronous, non-blocking code.
 */
public class Ex04CompletableFutureExample {
    public static void main(String[] args) {
        // A CompletableFuture that runs asynchronously
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            // Simulating a long-running task
            try {
                Thread.sleep(3000);
                System.out.println("Task completed in thread: " + Thread.currentThread().getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        // Main thread continues without waiting for future to complete
        System.out.println("Main thread: " + Thread.currentThread().getName() + " is not blocked");

        // Block the main thread until the future completes
        future.join();
    }
}
