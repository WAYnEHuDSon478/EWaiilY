// 代码生成时间: 2025-08-07 14:20:56
package com.example.randomnumber;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import java.util.concurrent.ThreadLocalRandom;

/**
 * RandomNumberGenerator class provides a REST API to generate random numbers.
 */
@Controller("/random")
@Introspected
public class RandomNumberGenerator {

    /**
     * Generates a random number within a specified range.
     *
     * @param min Minimum value of the range (inclusive)
     * @param max Maximum value of the range (exclusive)
     * @return A string representation of the random number.
     */
    @Get("/number")
    public String generateRandomNumber(Integer min, Integer max) {
        // Validate the input range
        if (min == null || max == null || min >= max) {
            throw new IllegalArgumentException("Invalid range. Max must be greater than min.");
        }

        try {
            int randomNumber = ThreadLocalRandom.current().nextInt(min, max);
            return String.valueOf(randomNumber);
        } catch (Exception e) {
            // Log and handle any unexpected errors
            throw new RuntimeException("Error generating random number", e);
        }
    }
}
