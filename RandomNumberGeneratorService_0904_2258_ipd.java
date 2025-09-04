// 代码生成时间: 2025-09-04 22:58:21
package com.example.randomservice;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.context.annotation.Service;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class RandomNumberGeneratorService {
    
    private static final int MAX_RANDOM_VALUE = 1000; // Define the maximum value for random numbers
    private static final int MIN_RANDOM_VALUE = 1;    // Define the minimum value for random numbers
    
    /**<ol>
     * Generates a random number between the specified minimum and maximum values.
     *
     * @param min Minimum value of the range.
     * @param max Maximum value of the range.
     * @return A random number between min and max (inclusive).
     * @throws IllegalArgumentException if min is greater than max.
     */
    public int generateRandomNumber(@NonNull int min, @NonNull int max) {
        if (min > max) {
            throw new IllegalArgumentException("Minimum value cannot be greater than maximum value");
        }
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }
}
