package com.coherentsolutions.advanced.java.section01;

/**
 * Ex02BlockingTaskExample demonstrates blocking behavior in threads using Thread.sleep()
 * and how it impacts the main thread when using join().
 */
class BlockingTask implements Runnable {
    @Override
    public void run() {
        try {
            // Simulate a blocking operation using Thread.sleep
            Thread.sleep(5000);  // Blocks the thread for 5 seconds
            System.out.println("Task completed in thread: " + Thread.currentThread().getName());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

public class Ex02BlockingTaskExample {
    public static void main(String[] args) throws InterruptedException {
        // Creating two threads that execute BlockingTask
        Thread thread1 = new Thread(new BlockingTask());
        Thread thread2 = new Thread(new BlockingTask());

        // Start the threads
        thread1.start();
        thread2.start();

        // Using join() to block the main thread until thread1 and thread2 finish execution
        thread1.join();  // Main thread waits for thread1
        thread2.join();  // Main thread waits for thread2

        System.out.println("Main thread: " + Thread.currentThread().getName() + " finished");
    }
}
