package com.coherentsolutions.advanced.java.section01;

/**
 * Ex01BasicThreadExample demonstrates how to create and start basic threads using the Runnable interface.
 */
class MyTask implements Runnable {
    @Override
    public void run() {
        // Output the name of the current thread executing this task
        System.out.println("Executing task in thread: " + Thread.currentThread().getName());
    }
}

public class Ex01BasicThreadExample {
    public static void main(String[] args) {
        // Creating and starting two threads
        Thread thread1 = new Thread(new MyTask());
        Thread thread2 = new Thread(new MyTask());

        // Start the threads
        thread1.start();  // This starts the execution of thread1
        thread2.start();  // This starts the execution of thread2

        // Print the main thread name to observe concurrent execution
        System.out.println("Main thread: " + Thread.currentThread().getName());
    }
}
