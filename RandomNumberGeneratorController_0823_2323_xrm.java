// 代码生成时间: 2025-08-23 23:23:55
package com.example.randomnumbergenerator;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.HttpRequest;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * This controller provides an endpoint to generate a random number.
 */
@Controller("/random")
public class RandomNumberGeneratorController {

    private final Random random = new Random();

    /**
     * Generates a random number between 1 and 100.
     *
     * @param request The HTTP request.
     * @return The generated random number.
     */
    @Get("/number")
    public int generateRandomNumber(HttpRequest request) {
        // Ensure thread safety by using ThreadLocalRandom if needed
        int randomNumber = ThreadLocalRandom.current().nextInt(1, 101);

        // Log the request and response for debugging purposes
        request.log().ifPresent(log -> log.info("Generating random number..."));

        // Return the generated random number
        return randomNumber;
    }

    /**
     * Generates a random number between two specified bounds.
     *
     * @param request The HTTP request.
     * @param lowerBound The lower bound of the random number range.
     * @param upperBound The upper bound of the random number range.
     * @return The generated random number.
     */
    @Get("/number/{lowerBound}/{upperBound}")
    public int generateRandomNumberInRange(HttpRequest request, @PathVariable int lowerBound, @PathVariable int upperBound) {
        // Validate the bounds to ensure they are not equal and lowerBound is less than upperBound
        if (lowerBound >= upperBound) {
            throw new IllegalArgumentException("Lower bound must be less than upper bound.");
        }

        // Generate a random number within the specified range
        int randomNumber = ThreadLocalRandom.current().nextInt(lowerBound, upperBound + 1);

        // Log the request and response for debugging purposes
        request.log().ifPresent(log -> log.info("Generating random number within range..."));

        // Return the generated random number
        return randomNumber;
    }
}
