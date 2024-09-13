package com.coherentsolutions.advanced.java.section02;

import java.util.List;
import java.util.stream.IntStream;

/**
 * Ex03ParallelStreamsExample demonstrates the use of parallel streams for parallel processing.
 */
public class Ex03ParallelStreamsExample {
    public static void main(String[] args) {
        // Create a stream of integers from 1 to 100
        IntStream range = IntStream.range(1, 101);

        // Process the stream in parallel
        List<Integer> results = range.parallel()
                .filter(n -> n % 2 == 0)  // Keep only even numbers
                .map(n -> n * n)  // Square each even number
                .boxed()  // Convert IntStream to Stream<Integer>
                .toList();  // Collect the result into a list

        // Print the results
        System.out.println("Processed list: " + results);
    }
}
