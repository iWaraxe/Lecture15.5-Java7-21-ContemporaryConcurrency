package com.coherentsolutions.advanced.java.section02;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Ex02CompletableFutureExample demonstrates asynchronous task chaining with CompletableFuture.
 */
public class Ex02CompletableFutureExample {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // First task: simulate a long-running I/O operation
        CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(() -> {
            System.out.println("Task 1 started in thread: " + Thread.currentThread().getName());
            try {
                Thread.sleep(2000); // Simulate a delay
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 10;  // Return a result
        });

        // Chain the second task, which depends on the result of the first task
        CompletableFuture<Integer> future2 = future1.thenApply(result -> {
            System.out.println("Task 2 started with result from Task 1: " + result);
            return result * 2;  // Modify the result
        });

        // Chain a third task to combine the results of future1 and future2
        CompletableFuture<Integer> finalResult = future2.thenCombine(future1, (result2, result1) -> {
            System.out.println("Combining results from Task 1 and Task 2");
            return result1 + result2;  // Combine both results
        });

        // Print the final result
        System.out.println("Final result: " + finalResult.get());
    }
}
