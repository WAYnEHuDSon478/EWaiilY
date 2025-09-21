// 代码生成时间: 2025-09-21 21:44:24
package com.example.randomnumbergenerator;

import io.micronaut.context.annotation.Bean;
import io.micronaut.context.annotation.Executable;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Service class for generating random numbers.
 */
public class RandomNumberGeneratorService {

    /**
     * Generates a random number between the given range.
     *
     * @param min The minimum value of the range.
     * @param max The maximum value of the range.
     * @return A random number between min and max.
     */
    @Bean
    @Executable
    public Integer generateRandomNumber(Integer min, Integer max) {
        // Check if the input values are valid
        if (min == null || max == null || min > max) {
            throw new IllegalArgumentException("Invalid input values. Min and max should be non-null and min should be less than or equal to max.");
        }

        // Generate a random number between min and max
        int randomNumber = ThreadLocalRandom.current().nextInt(min, max + 1);

        return randomNumber;
    }
}
