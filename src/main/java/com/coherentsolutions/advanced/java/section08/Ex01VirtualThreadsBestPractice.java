package com.coherentsolutions.advanced.java.section08;

import java.util.concurrent.Executors;

/**
 * Ex01VirtualThreadsBestPractice demonstrates the use of virtual threads to handle large numbers of tasks efficiently.
 */
public class Ex01VirtualThreadsBestPractice {
    public static void main(String[] args) throws InterruptedException {
        // Using virtual threads to handle a large number of tasks
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            for (int i = 0; i < 1000; i++) {
                executor.submit(() -> {
                    System.out.println("Task running in: " + Thread.currentThread().getName());
                });
            }
        }

        System.out.println("Main thread completed.");
    }
}
