package com.coherentsolutions.advanced.java.section03;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * Ex02CompletableFutureTimeoutExample demonstrates timeout handling and asynchronous completion using CompletableFuture.
 */
public class Ex02CompletableFutureTimeoutExample {
    public static void main(String[] args) throws Exception {
        // Simulate a long-running task with timeout handling using orTimeout
        CompletableFuture<String> futureWithTimeout = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(5000);  // Simulate a long computation
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "Completed Task";
        }).orTimeout(3, TimeUnit.SECONDS);  // Will timeout if the task takes longer than 3 seconds

        futureWithTimeout
                .exceptionally(ex -> "Task timed out: " + ex.getMessage())  // Handle timeout
                .thenAccept(System.out::println);

        // Example of asynchronous completion using completeAsync
        CompletableFuture<String> futureCompletedAsync = new CompletableFuture<>();

        futureCompletedAsync.completeAsync(() -> "Async Completion").thenAccept(result -> {
            System.out.println("Future completed asynchronously with result: " + result);
        });

        // Allow time for tasks to complete
        Thread.sleep(6000);
    }
}
