package com.coherentsolutions.advanced.java.section10;

import java.util.concurrent.StructuredTaskScope;

/**
 * Ex02DataPipelineWithStructuredConcurrency demonstrates the use of structured concurrency
 * to manage tasks in a data processing pipeline. Multiple stages of the pipeline are run concurrently.
 */
public class Ex02DataPipelineWithStructuredConcurrency {
    public static void main(String[] args) throws Exception {
        // Use structured concurrency to manage the data processing pipeline
        try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {

            var task1 = scope.fork(() -> processData("Stage 1: Fetch data from database", 1000));
            var task2 = scope.fork(() -> processData("Stage 2: Transform data", 1500));
            var task3 = scope.fork(() -> processData("Stage 3: Save results to file", 2000));

            // Wait for all tasks to complete or fail
            scope.join();
            scope.throwIfFailed();

            // If all tasks succeed, print their results
            System.out.println("Task 1 result: " + task1.get());
            System.out.println("Task 2 result: " + task2.get());
            System.out.println("Task 3 result: " + task3.get());
        }
    }

    // Simulate a task in the data pipeline
    private static String processData(String taskName, int delay) throws InterruptedException {
        Thread.sleep(delay);  // Simulate task delay
        System.out.println(taskName + " completed.");
        return taskName + " result";
    }
}
