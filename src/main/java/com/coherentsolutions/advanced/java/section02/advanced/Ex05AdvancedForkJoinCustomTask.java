package com.coherentsolutions.advanced.java.section02.advanced;

import java.util.concurrent.RecursiveAction;
import java.util.concurrent.ForkJoinPool;

/**
 * Ex05AdvancedForkJoinCustomTask demonstrates a custom Fork/Join task using RecursiveAction.
 */
class CustomTask extends RecursiveAction {
    private final int[] array;
    private final int start;
    private final int end;
    private static final int THRESHOLD = 10;

    public CustomTask(int[] array, int start, int end) {
        this.array = array;
        this.start = start;
        this.end = end;
    }

    @Override
    protected void compute() {
        if (end - start <= THRESHOLD) {
            // Simple action: multiply each element by 2
            for (int i = start; i < end; i++) {
                array[i] *= 2;
            }
        } else {
            // Split the task and fork new tasks
            int mid = (start + end) / 2;
            CustomTask leftTask = new CustomTask(array, start, mid);
            CustomTask rightTask = new CustomTask(array, mid, end);
            invokeAll(leftTask, rightTask); // Fork both tasks
        }
    }
}

public class Ex05AdvancedForkJoinCustomTask {
    public static void main(String[] args) {
        int[] array = new int[100]; // Example array

        for (int i = 0; i < array.length; i++) {
            array[i] = i + 1;
        }

        ForkJoinPool pool = new ForkJoinPool();
        CustomTask task = new CustomTask(array, 0, array.length);
        pool.invoke(task);  // Execute the Fork/Join task

        // Print the modified array
        for (int i : array) {
            System.out.print(i + " ");
        }
    }
}
