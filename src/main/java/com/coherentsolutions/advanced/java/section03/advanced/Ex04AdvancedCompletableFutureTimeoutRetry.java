package com.coherentsolutions.advanced.java.section03.advanced;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * Ex04AdvancedCompletableFutureTimeoutRetry demonstrates a more advanced use of CompletableFuture with retry logic and timeout handling.
 */
public class Ex04AdvancedCompletableFutureTimeoutRetry {
    public static void main(String[] args) throws Exception {
        // Simulate a task with retry logic and timeout handling
        CompletableFuture<Object> future = retryTaskWithTimeout(3)
                .exceptionally(ex -> "Final failure: " + ex.getMessage());

        future.thenAccept(System.out::println);

        // Allow time for tasks to complete
        Thread.sleep(6000);
    }

    // Retry the task up to maxRetries times if it fails
    private static CompletableFuture<Object> retryTaskWithTimeout(int maxRetries) {
        return CompletableFuture.supplyAsync(() -> {
                    System.out.println("Executing task...");
                    try {
                        Thread.sleep(5000);  // Simulate long task
                        throw new RuntimeException("Task failed");
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }).orTimeout(3, TimeUnit.SECONDS)  // Timeout after 3 seconds
                .handle((result, ex) -> {
                    if (ex != null && maxRetries > 0) {
                        System.out.println("Retrying task due to: " + ex.getMessage());
                        return retryTaskWithTimeout(maxRetries - 1).join();  // Retry
                    } else if (ex != null) {
                        return "Task ultimately failed after retries: " + ex.getMessage();
                    }
                    return result;
                });
    }
}
