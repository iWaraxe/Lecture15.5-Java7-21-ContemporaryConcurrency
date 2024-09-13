package com.coherentsolutions.advanced.java.section09;

import java.lang.ScopedValue;
import java.util.concurrent.Executors;

/**
 * Ex03ScopedValuesVsThreadLocal demonstrates how to use Scoped Values in virtual threads instead of ThreadLocal.
 */
public class Ex03ScopedValuesVsThreadLocal {
    // ScopedValue example to share immutable data across virtual threads
    private static final ScopedValue<String> USER_CONTEXT = ScopedValue.newInstance();

    public static void main(String[] args) throws InterruptedException {
        // Traditional approach with ThreadLocal (not suitable for virtual threads)
        ThreadLocal<String> threadLocal = ThreadLocal.withInitial(() -> "User A");

        // Scoped Value with virtual threads (preferred)
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            for (int i = 0; i < 10; i++) {
                final int taskId = i;
                executor.submit(() -> {
                    ScopedValue.where(USER_CONTEXT, "User " + taskId).run(() -> {
                        System.out.println("Task " + taskId + " running for: " + USER_CONTEXT.get());
                    });
                });
            }
        }
    }
}
