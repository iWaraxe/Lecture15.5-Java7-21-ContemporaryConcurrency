package com.coherentsolutions.advanced.java.section06;

import java.util.concurrent.Executors;

/**
 * Ex02VirtualThreadExample demonstrates the creation and management of virtual threads introduced in Project Loom.
 */
public class Ex02VirtualThreadExample {
    public static void main(String[] args) throws InterruptedException {
        // Create a virtual thread executor to manage virtual threads
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            // Submit 10 virtual threads
            for (int i = 0; i < 10; i++) {
                executor.submit(() -> {
                    System.out.println("Virtual Thread running: " + Thread.currentThread().getName());
                });
            }
        }

        System.out.println("Main thread completed.");
    }
}
