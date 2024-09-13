package com.coherentsolutions.advanced.java.section01;

/**
 * Ex03HighLoadThreadExample demonstrates the overhead caused by creating a large number of threads.
 * This shows how traditional threads may cause performance issues and even lead to OutOfMemoryError.
 */
public class Ex03HighLoadThreadExample {
    public static void main(String[] args) {
        // Simulating a system under heavy load by creating many threads
        for (int i = 0; i < 10000; i++) {
            Thread thread = new Thread(() -> {
                try {
                    // Simulate work done by each thread
                    Thread.sleep(100);  // Each thread sleeps for 100 milliseconds
                    System.out.println("Thread finished: " + Thread.currentThread().getName());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });

            // Start each thread
            thread.start();
        }

        // Print when the main thread completes
        System.out.println("Main thread completed.");
    }
}
