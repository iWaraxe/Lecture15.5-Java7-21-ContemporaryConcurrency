package com.coherentsolutions.advanced.java.section06;

/**
 * Ex01TraditionalThreadExample demonstrates the use of traditional platform threads (OS threads).
 */
public class Ex01TraditionalThreadExample {
    public static void main(String[] args) {
        // Creating a platform thread using Thread class
        Thread platformThread = new Thread(() -> {
            System.out.println("Platform Thread running: " + Thread.currentThread().getName());
        });

        // Starting the platform thread
        platformThread.start();

        // Wait for the thread to finish
        try {
            platformThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Main thread completed.");
    }
}
