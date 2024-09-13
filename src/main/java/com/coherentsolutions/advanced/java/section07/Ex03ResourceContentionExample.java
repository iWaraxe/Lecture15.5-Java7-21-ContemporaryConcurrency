package com.coherentsolutions.advanced.java.section07;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Ex03ResourceContentionExample demonstrates the difference between CPU-bound
 * and I/O-bound tasks and how thread management should differ for each.
 */
public class Ex03ResourceContentionExample {

    // Simulate a CPU-bound task (intensive computation)
    public static void cpuBoundTask() {
        long sum = 0;
        for (long i = 0; i < 1_000_000_000L; i++) {
            sum += i;
        }
        System.out.println("CPU-bound task completed by: " + Thread.currentThread().getName());
    }

    // Simulate an I/O-bound task (waiting for external resources)
    public static void ioBoundTask() {
        try {
            Thread.sleep(2000);  // Simulating an I/O delay
            System.out.println("I/O-bound task completed by: " + Thread.currentThread().getName());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(4);

        // Submit CPU-bound tasks
        for (int i = 0; i < 4; i++) {
            executor.submit(Ex03ResourceContentionExample::cpuBoundTask);
        }

        // Submit I/O-bound tasks
        for (int i = 0; i < 4; i++) {
            executor.submit(Ex03ResourceContentionExample::ioBoundTask);
        }

        executor.shutdown();
    }
}
