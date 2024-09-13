package com.coherentsolutions.advanced.java.section06;

import java.util.concurrent.CompletableFuture;

/**
 * Ex03AsynchronousProgrammingExample demonstrates the use of CompletableFuture for asynchronous tasks.
 */
public class Ex03AsynchronousProgrammingExample {
    public static void main(String[] args) throws Exception {
        // Creating an asynchronous task using CompletableFuture
        CompletableFuture.supplyAsync(() -> {
                    System.out.println("Asynchronous task running in: " + Thread.currentThread().getName());
                    return "Task Result";
                })
                .thenAccept(result -> {
                    // Handling the result of the asynchronous task
                    System.out.println("Received result: " + result);
                })
                .exceptionally(ex -> {
                    // Handling errors in the asynchronous task
                    System.err.println("Error occurred: " + ex.getMessage());
                    return null;
                })
                .join(); // Block the main thread until the task is completed

        System.out.println("Main thread completed.");
    }
}
