package com.coherentsolutions.advanced.java.section03;

/**
 * Ex03ThreadLocalExample demonstrates the concept of using ThreadLocal to store thread-specific data.
 */
public class Ex03ThreadLocalExample {
    // ThreadLocal variable to store user context per thread
    private static ThreadLocal<String> userContext = ThreadLocal.withInitial(() -> "Unknown User");

    public static void main(String[] args) throws InterruptedException {
        // Simulate two threads working on different user contexts
        Thread thread1 = new Thread(() -> {
            userContext.set("User A");
            process();
        });

        Thread thread2 = new Thread(() -> {
            userContext.set("User B");
            process();
        });

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        // Demonstrating that ThreadLocal does not leak data between threads
        System.out.println("Main thread: " + userContext.get());  // Will print "Unknown User"
    }

    // Simulated processing with thread-local data
    private static void process() {
        System.out.println("Processing for: " + userContext.get());
    }
}
