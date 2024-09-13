package com.coherentsolutions.advanced.java.section05.advanced;

import java.lang.ScopedValue;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Ex03ScopedValuesPerformanceComparison compares the performance between Scoped Values and ThreadLocal
 * by simulating a high number of threads, demonstrating the memory efficiency of Scoped Values.
 */
public class Ex03ScopedValuesPerformanceComparison {
    // Define a ScopedValue for sharing immutable data
    private static final ScopedValue<String> SCOPED_USER_CONTEXT = ScopedValue.newInstance();

    // Define a ThreadLocal for comparison
    private static final ThreadLocal<String> THREAD_LOCAL_USER_CONTEXT = ThreadLocal.withInitial(() -> "Default User");

    public static void main(String[] args) throws Exception {
        // Run ScopedValues performance test
        System.out.println("Running Scoped Values test...");
        runScopedValuesTest();

        // Run ThreadLocal performance test
        System.out.println("Running ThreadLocal test...");
        runThreadLocalTest();
    }

    // Scoped Values Test
    private static void runScopedValuesTest() throws Exception {
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            // Submit tasks and collect futures
            List<Future<Object>> futures = IntStream.range(1, 10001)
                    .mapToObj(i -> executor.submit(() -> {
                        ScopedValue.where(SCOPED_USER_CONTEXT, "User " + i).run(() -> {
                            // Simulate some work using the scoped value
                            System.out.println("Scoped Value Thread " + i + ": " + SCOPED_USER_CONTEXT.get());
                        });
                        return null;  // Return null to match Future<Void> type
                    }))
                    .collect(Collectors.toList());

            // Wait for all threads to complete
            for (Future<Object> future : futures) {
                future.get();  // Wait for each future to complete
            }
        }
    }

    // ThreadLocal Test
    private static void runThreadLocalTest() throws Exception {
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            // Submit tasks and collect futures
            List<Future<Object>> futures = IntStream.range(1, 10001)
                    .mapToObj(i -> executor.submit(() -> {
                        THREAD_LOCAL_USER_CONTEXT.set("User " + i);
                        // Simulate some work using the ThreadLocal value
                        System.out.println("ThreadLocal Thread " + i + ": " + THREAD_LOCAL_USER_CONTEXT.get());
                        return null;  // Return null to match Future<Void> type
                    }))
                    .collect(Collectors.toList()).reversed();

            // Wait for all threads to complete
            for (Future<Object> future : futures) {
                future.get();  // Wait for each future to complete
            }
        }
    }
}
