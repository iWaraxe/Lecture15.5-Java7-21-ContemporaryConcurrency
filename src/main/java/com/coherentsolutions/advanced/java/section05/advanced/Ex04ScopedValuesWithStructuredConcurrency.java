package com.coherentsolutions.advanced.java.section05.advanced;

import java.lang.ScopedValue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.StructuredTaskScope;

/**
 * Ex04ScopedValuesWithStructuredConcurrency demonstrates how Scoped Values can be used with Structured Concurrency
 * to share immutable data across multiple concurrent tasks, while maintaining data consistency.
 */
public class Ex04ScopedValuesWithStructuredConcurrency {
    // Define a ScopedValue for sharing immutable configuration data between tasks
    private static final ScopedValue<String> CONFIGURATION = ScopedValue.newInstance();

    public static void main(String[] args) throws Exception {
        // Define the configuration for this execution context
        ScopedValue.where(CONFIGURATION, "App Configuration A").run(() -> {
            try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
                // Fork multiple tasks, each of which inherits the Scoped Value
                var task1 = scope.fork(() -> processTask("Task 1"));
                var task2 = scope.fork(() -> processTask("Task 2"));

                // Wait for both tasks to complete
                scope.join().throwIfFailed();

                // Output the results
                System.out.println("Result from task 1: " + task1.get());
                System.out.println("Result from task 2: " + task2.get());
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }

    // Simulate a task that uses the scoped value
    private static String processTask(String taskName) {
        System.out.println(taskName + " is running with configuration: " + CONFIGURATION.get());
        return taskName + " completed";
    }
}
