package com.coherentsolutions.advanced.java.section01.advanced;

import java.util.concurrent.RecursiveTask;
import java.util.concurrent.ForkJoinPool;

/**
 * Ex05ForkJoinExample demonstrates the use of Fork/Join Framework for parallel task processing.
 */
class SumTask extends RecursiveTask<Integer> {
    private final int[] array;
    private final int start;
    private final int end;

    // Threshold for splitting the task
    private static final int THRESHOLD = 5;

    public SumTask(int[] array, int start, int end) {
        this.array = array;
        this.start = start;
        this.end = end;
    }

    @Override
    protected Integer compute() {
        if (end - start <= THRESHOLD) {
            // Perform the task directly if below threshold
            int sum = 0;
            for (int i = start; i < end; i++) {
                sum += array[i];
            }
            return sum;
        } else {
            // Split the task
            int mid = (start + end) / 2;
            SumTask leftTask = new SumTask(array, start, mid);
            SumTask rightTask = new SumTask(array, mid, end);

            // Fork the subtasks
            leftTask.fork();
            int rightResult = rightTask.compute();
            int leftResult = leftTask.join();

            // Combine the results
            return leftResult + rightResult;
        }
    }
}

public class Ex05ForkJoinExample {
    public static void main(String[] args) {
        // Example array to sum
        int[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

        // Create a ForkJoinPool
        ForkJoinPool forkJoinPool = new ForkJoinPool();

        // Create the root task for summing the array
        SumTask sumTask = new SumTask(array, 0, array.length);

        // Execute the task in the ForkJoinPool
        int result = forkJoinPool.invoke(sumTask);

        // Print the result
        System.out.println("Sum of array: " + result);
    }
}
