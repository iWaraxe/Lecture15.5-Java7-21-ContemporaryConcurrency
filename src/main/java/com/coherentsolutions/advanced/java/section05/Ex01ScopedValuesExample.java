package com.coherentsolutions.advanced.java.section05;

import java.lang.ScopedValue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.StructuredTaskScope;

/**
 * Ex01ScopedValuesExample demonstrates the use of Scoped Values for sharing immutable data
 * between threads in a safe and efficient manner using virtual threads.
 */
public class Ex01ScopedValuesExample {
    // Define a ScopedValue for sharing immutable user data between threads
    private static final ScopedValue<String> USER_CONTEXT = ScopedValue.newInstance();

    public static void main(String[] args) throws Exception {
        // Simulate handling a user request in a virtual thread
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            executor.submit(() -> {
                // Bind data to the current scope using ScopedValue.where()
                ScopedValue.where(USER_CONTEXT, "User A").run(() -> {
                    System.out.println("In virtual thread: " + Thread.currentThread().getName());
                    System.out.println("Processing for user: " + USER_CONTEXT.get());

                    // Further tasks can inherit the scoped value
                    try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
                        var task = scope.fork(() -> {
                            // The scoped value is available in the forked task as well
                            System.out.println("Forked task for user: " + USER_CONTEXT.get());
                            return USER_CONTEXT.get();
                        });
                        scope.join().throwIfFailed();
                        System.out.println("Result from forked task: " + task.get());
                    } catch (ExecutionException e) {
                        throw new RuntimeException(e);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                });
            }).get();
        }

        System.out.println("Main thread completed.");
    }
}
