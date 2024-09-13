package com.coherentsolutions.advanced.java.section02;

import java.util.concurrent.RecursiveTask;
import java.util.concurrent.ForkJoinPool;

/**
 * Ex01ForkJoinExample demonstrates the Fork/Join framework to compute the sum of an array
 * using the divide-and-conquer approach.
 */
class SumTask extends RecursiveTask<Integer> {
    private final int[] array;
    private final int start;
    private final int end;

    // Threshold for when to stop dividing tasks
    private static final int THRESHOLD = 10;

    public SumTask(int[] array, int start, int end) {
        this.array = array;
        this.start = start;
        this.end = end;
    }

    @Override
    protected Integer compute() {
        // If task size is small enough, compute directly
        if (end - start <= THRESHOLD) {
            int sum = 0;
            for (int i = start; i < end; i++) {
                sum += array[i];
            }
            return sum;
        } else {
            // Otherwise, split the task
            int mid = (start + end) / 2;
            SumTask leftTask = new SumTask(array, start, mid);
            SumTask rightTask = new SumTask(array, mid, end);

            // Fork the left task (execute in parallel) and compute the right one in current thread
            leftTask.fork();
            int rightResult = rightTask.compute();
            int leftResult = leftTask.join(); // Wait for the left task to finish

            // Combine the results
            return leftResult + rightResult;
        }
    }
}

public class Ex01ForkJoinExample {
    public static void main(String[] args) {
        int[] array = new int[100];  // Create an array with 100 integers

        // Fill the array with values from 1 to 100
        for (int i = 0; i < array.length; i++) {
            array[i] = i + 1;
        }

        // Create a ForkJoinPool to manage the tasks
        ForkJoinPool pool = new ForkJoinPool();

        // Create the root task
        SumTask task = new SumTask(array, 0, array.length);

        // Invoke the task and get the result
        int sum = pool.invoke(task);

        // Print the result
        System.out.println("Sum of the array: " + sum);
    }
}
