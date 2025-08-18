// 代码生成时间: 2025-08-18 13:37:20
package com.yourpackage;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.HttpResponse;
import java.util.Random;
import java.util.UUID;

@Controller("/testData")
@Introspected
public class TestDataGenerator {

    private final Random random = new Random();

    /**
     * Generates and returns a random string.
     *
     * @param length The length of the string to generate.
     * @return A random string of the specified length.
     */
    private String generateRandomString(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            char c = (char) (random.nextInt(26) + 'a');
            sb.append(c);
        }
        return sb.toString();
    }

    /**
     * Generates and returns a random UUID.
     *
     * @return A random UUID.
     */
    private String generateRandomUUID() {
        return UUID.randomUUID().toString();
    }

    /**
     * Handles GET requests to the /testData endpoint and returns a
     * JSON response with generated test data.
     *
     * @return A JSON response with test data.
     */
    @Get("/")
    public HttpResponse<String> generateTestData() {
        try {
            String randomString = generateRandomString(10);
            String randomUUID = generateRandomUUID();

            // Construct the response JSON
            String jsonData = String.format(
                "{
  "randomString": "%s",
  "randomUUID": "%s"
}",
                randomString,
                randomUUID
            );

            return HttpResponse.ok(jsonData);
        } catch (Exception e) {
            // Handle any exceptions that may occur
            return HttpResponse.serverError();
        }
    }
}
