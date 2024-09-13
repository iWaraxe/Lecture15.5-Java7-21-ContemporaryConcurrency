package com.coherentsolutions.advanced.java.section01.advanced;

import java.time.Duration;
import java.util.stream.IntStream;
import java.util.concurrent.Executors;

/**
 * Ex06VirtualThreadExample demonstrates the use of virtual threads introduced by Project Loom for efficient concurrency.
 */
public class Ex06VirtualThreadExample {
    public static void main(String[] args) {
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            // Submit 1000 tasks to the virtual thread executor
            IntStream.range(0, 1000).forEach(i -> {
                executor.submit(() -> {
                    try {
                        Thread.sleep(Duration.ofSeconds(1));
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println("Task " + i + " completed in thread: " + Thread.currentThread().getName());
                });
            });
        }

        System.out.println("Main thread completed.");
    }
}
