// 代码生成时间: 2025-08-14 23:20:51
package com.example.demo.service;

import io.micronaut.core.annotation.Indexed;
import io.micronaut.core.annotation.NonNull;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class to perform sorting operations.
 */
@Indexed
public class SortingService {

    /**
     * Sorts a list of integers using bubble sort algorithm.
     *
     * @param numbers List of integers to be sorted.
     * @return sorted list of integers.
     * @throws IllegalArgumentException if the input list is null or empty.
     */
    public List<Integer> bubbleSort(@NonNull List<Integer> numbers) {
        if (numbers == null || numbers.isEmpty()) {
            throw new IllegalArgumentException("Input list cannot be null or empty.");
        }

        boolean swapped;
        do {
            swapped = false;
            for (int i = 1; i < numbers.size(); i++) {
                if (numbers.get(i - 1) > numbers.get(i)) {
                    // Swap numbers.get(i - 1) and numbers.get(i)
                    int temp = numbers.get(i - 1);
                    numbers.set(i - 1, numbers.get(i));
                    numbers.set(i, temp);
                    swapped = true;
                }
            }
        } while (swapped);
        return numbers;
    }

    /**
     * Sorts a list of integers using selection sort algorithm.
     *
     * @param numbers List of integers to be sorted.
     * @return sorted list of integers.
     * @throws IllegalArgumentException if the input list is null or empty.
     */
    public List<Integer> selectionSort(@NonNull List<Integer> numbers) {
        if (numbers == null || numbers.isEmpty()) {
            throw new IllegalArgumentException("Input list cannot be null or empty.");
        }

        for (int i = 0; i < numbers.size() - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < numbers.size(); j++) {
                if (numbers.get(j) < numbers.get(minIndex)) {
                    minIndex = j;
                }
            }
            // Swap numbers.get(minIndex) and numbers.get(i)
            int temp = numbers.get(minIndex);
            numbers.set(minIndex, numbers.get(i));
            numbers.set(i, temp);
        }
        return numbers;
    }

    /**
     * Sorts a list of integers using insertion sort algorithm.
     *
     * @param numbers List of integers to be sorted.
     * @return sorted list of integers.
     * @throws IllegalArgumentException if the input list is null or empty.
     */
    public List<Integer> insertionSort(@NonNull List<Integer> numbers) {
        if (numbers == null || numbers.isEmpty()) {
            throw new IllegalArgumentException("Input list cannot be null or empty.");
        }

        for (int i = 1; i < numbers.size(); i++) {
            int key = numbers.get(i);
            int j = i - 1;

            while (j >= 0 && numbers.get(j) > key) {
                numbers.set(j + 1, numbers.get(j));
                j = j - 1;
            }
            numbers.set(j + 1, key);
        }
        return numbers;
    }

    /**
     * Sorts a list of integers using quicksort algorithm.
     *
     * @param numbers List of integers to be sorted.
     * @return sorted list of integers.
     * @throws IllegalArgumentException if the input list is null or empty.
     */
    public List<Integer> quickSort(@NonNull List<Integer> numbers) {
        if (numbers == null || numbers.isEmpty()) {
            throw new IllegalArgumentException("Input list cannot be null or empty.");
        }

        return quickSort(numbers, 0, numbers.size() - 1);
    }

    private List<Integer> quickSort(List<Integer> numbers, int low, int high) {
        if (low < high) {
            int pi = partition(numbers, low, high);

            quickSort(numbers, low, pi - 1);
            quickSort(numbers, pi + 1, high);
        }
        return numbers;
    }

    private int partition(List<Integer> numbers, int low, int high) {
        int pivot = numbers.get(high);
        int i = (low - 1);
        for (int j = low; j < high; j++) {
            if (numbers.get(j) < pivot) {
                i++;
                // Swap numbers.get(i) and numbers.get(j)
                int temp = numbers.get(i);
                numbers.set(i, numbers.get(j));
                numbers.set(j, temp);
            }
        }
        // Swap numbers.get(i+1) and numbers.get(high)
        int temp = numbers.get(i + 1);
        numbers.set(i + 1, numbers.get(high));
        numbers.set(high, temp);
        return i + 1;
    }
}