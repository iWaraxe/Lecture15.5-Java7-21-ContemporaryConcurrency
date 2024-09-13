package com.coherentsolutions.advanced.java.section11;

import java.util.concurrent.Executors;
import java.net.Socket;

/**
 * Ex01IOBoundTasksWithVirtualThreads demonstrates how virtual threads handle I/O-bound tasks effectively.
 * Each task simulates a connection to an external server, showing how virtual threads can manage
 * many I/O operations concurrently without blocking critical resources.
 */
public class Ex01IOBoundTasksWithVirtualThreads {
    public static void main(String[] args) throws Exception {
        // Using a virtual thread executor to handle large numbers of I/O-bound tasks
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            for (int i = 0; i < 1000; i++) {
                executor.submit(() -> {
                    try (var socket = new Socket("example.com", 80)) {
                        // Simulate I/O operation by connecting to a socket
                        System.out.println("Task handled by virtual thread: " + Thread.currentThread().getName());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            }
        }

        System.out.println("All I/O tasks handled successfully using virtual threads.");
    }
}
