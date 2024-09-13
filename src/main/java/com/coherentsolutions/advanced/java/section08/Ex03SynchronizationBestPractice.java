package com.coherentsolutions.advanced.java.section08;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Ex03SynchronizationBestPractice demonstrates how to properly synchronize access to shared resources to avoid race conditions.
 */
public class Ex03SynchronizationBestPractice {
    private static final ReentrantLock lock = new ReentrantLock();
    private static int sharedCounter = 0;

    // Increment the counter with synchronization to avoid race conditions
    public static void incrementCounter() {
        lock.lock();  // Acquire the lock
        try {
            sharedCounter++;
        } finally {
            lock.unlock();  // Release the lock
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                incrementCounter();
            }
        });

        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                incrementCounter();
            }
        });

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        System.out.println("Final Counter Value: " + sharedCounter);  // Should print 2000
    }
}
