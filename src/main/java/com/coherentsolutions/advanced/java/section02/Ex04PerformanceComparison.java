package com.coherentsolutions.advanced.java.section02;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * Ex04PerformanceComparison compares performance of Fork/Join, CompletableFuture, and traditional multithreading.
 */
class SumTaskForComparison extends RecursiveTask<Integer> {
    private final int[] array;
    private final int start;
    private final int end;

    private static final int THRESHOLD = 10;

    public SumTaskForComparison(int[] array, int start, int end) {
        this.array = array;
        this.start = start;
        this.end = end;
    }

    @Override
    protected Integer compute() {
        if (end - start <= THRESHOLD) {
            int sum = 0;
            for (int i = start; i < end; i++) {
                sum += array[i];
            }
            return sum;
        } else {
            int mid = (start + end) / 2;
            SumTaskForComparison leftTask = new SumTaskForComparison(array, start, mid);
            SumTaskForComparison rightTask = new SumTaskForComparison(array, mid, end);
            leftTask.fork();
            int rightResult = rightTask.compute();
            int leftResult = leftTask.join();
            return leftResult + rightResult;
        }
    }
}

public class Ex04PerformanceComparison {
    public static void main(String[] args) throws Exception {
        int[] array = new int[10000000];
        for (int i = 0; i < array.length; i++) {
            array[i] = i + 1;
        }

        int traditionalSum = 0;
        long startTime = System.nanoTime();
        for (int i = 0; i < array.length; i++) {
            traditionalSum += array[i];
        }
        long endTime = System.nanoTime();
        System.out.println("Main took: " + (endTime - startTime)/ 1_000 + " mks");



        // ForkJoin Example
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        startTime = System.nanoTime();
        SumTaskForComparison task = new SumTaskForComparison(array, 0, array.length);
        forkJoinPool.invoke(task);
        endTime = System.nanoTime();
        System.out.println("ForkJoin took: " + (endTime - startTime) / 1_000 + " mks");

        // CompletableFuture Example
        startTime = System.nanoTime();
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            int sum = 0;
            for (int i = 0; i < array.length; i++) {
                sum += array[i];
            }
            System.out.println("CompletableFuture result: " + sum);
        });
        future.join();
        endTime = System.nanoTime();
        System.out.println("CompletableFuture took: " + (endTime - startTime) / 1_000 + " mks");
    }
}
