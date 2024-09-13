package com.coherentsolutions.advanced.java.section06;

import java.util.concurrent.CompletableFuture;

/**
 * Ex05CompletableFutureExample demonstrates the chaining of asynchronous tasks using CompletableFuture.
 */
public class Ex05CompletableFutureExample {
    public static void main(String[] args) throws Exception {
        // Define two asynchronous tasks
        CompletableFuture<String> task1 = CompletableFuture.supplyAsync(() -> {
            System.out.println("Task 1 running");
            return "Task 1 result";
        });

        CompletableFuture<String> task2 = CompletableFuture.supplyAsync(() -> {
            System.out.println("Task 2 running");
            return "Task 2 result";
        });

        // Combine the results of both tasks
        CompletableFuture<String> combined = task1.thenCombine(task2, (result1, result2) -> {
            return result1 + " & " + result2;
        });

        // Get the combined result and print it
        System.out.println("Combined result: " + combined.get());
    }
}
