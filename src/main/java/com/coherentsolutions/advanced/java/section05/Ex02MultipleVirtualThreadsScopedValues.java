package com.coherentsolutions.advanced.java.section05;

import java.lang.ScopedValue;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Ex02MultipleVirtualThreadsScopedValues demonstrates the use of Scoped Values across multiple virtual threads,
 * allowing each thread to have its own immutable user context.
 */
public class Ex02MultipleVirtualThreadsScopedValues {
    // Define a ScopedValue for sharing immutable data (e.g., user session or configuration)
    private static final ScopedValue<String> USER_CONTEXT = ScopedValue.newInstance();

    public static void main(String[] args) throws Exception {
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            // Submit 10 virtual threads, each with its own user context
            List<Future<Object>> futures = IntStream.range(1, 11)
                    .mapToObj(i -> executor.submit(() -> {
                        // Each virtual thread gets its own immutable user context
                        ScopedValue.where(USER_CONTEXT, "User " + i).run(() -> {
                            System.out.println("Virtual Thread: " + Thread.currentThread().getName() +
                                    " processing for: " + USER_CONTEXT.get());
                        });
                        return null;  // Explicitly return null to match Future<Void> type
                    }))
                    .collect(Collectors.toList());

            // Wait for all threads to complete
            for (Future<Object> future : futures) {
                future.get();  // Wait for each future to complete
            }
        }

        System.out.println("Main thread completed.");
    }
}
