package com.coherentsolutions.advanced.java.section07;

/**
 * Ex01RaceConditionExample demonstrates the use of synchronized methods to prevent race conditions
 * when multiple threads try to access and modify a shared resource.
 */
public class Ex01RaceConditionExample {
    private static int counter = 0;

    // Synchronized method ensures that only one thread can modify the counter at a time
    public static synchronized void increment() {
        counter++;
    }

    public static void main(String[] args) throws InterruptedException {
        // Create two threads that will try to increment the shared counter
        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                increment();
            }
        });

        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                increment();
            }
        });

        // Start the threads
        thread1.start();
        thread2.start();

        // Wait for both threads to complete
        thread1.join();
        thread2.join();

        // Print the final value of the counter
        System.out.println("Final Counter Value: " + counter);  // Should print 2000
    }
}
