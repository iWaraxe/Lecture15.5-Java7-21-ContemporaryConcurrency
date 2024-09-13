package com.coherentsolutions.advanced.java.section07;

/**
 * Ex02DeadlockExample demonstrates how deadlocks can occur when threads
 * acquire locks in different orders.
 */
public class Ex02DeadlockExample {
    private static final Object LOCK1 = new Object();
    private static final Object LOCK2 = new Object();

    public static void main(String[] args) {
        // Thread 1: Acquires LOCK1 first, then LOCK2
        Thread thread1 = new Thread(() -> {
            synchronized (LOCK1) {
                System.out.println("Thread 1 acquired LOCK1");
                try { Thread.sleep(50); } catch (InterruptedException e) { }

                synchronized (LOCK2) {
                    System.out.println("Thread 1 acquired LOCK2");
                }
            }
        });

        // Thread 2: Acquires LOCK2 first, then LOCK1
        Thread thread2 = new Thread(() -> {
            synchronized (LOCK2) {
                System.out.println("Thread 2 acquired LOCK2");
                try { Thread.sleep(50); } catch (InterruptedException e) { }

                synchronized (LOCK1) {
                    System.out.println("Thread 2 acquired LOCK1");
                }
            }
        });

        // Start both threads
        thread1.start();
        thread2.start();
    }
}
