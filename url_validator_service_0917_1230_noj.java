// 代码生成时间: 2025-09-17 12:30:30
package com.example.validator;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.scheduling.TaskExecutors;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.net.URI;
import java.net.http.HttpClient as JavaHttpClient;
import java.net.http.HttpRequest as JavaHttpRequest;
import java.net.http.HttpResponse as JavaHttpResponse;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

/**
 * Service class to validate the URL link validity.
 */
@Singleton
public class UrlValidatorService {

    @Inject
    private HttpClient httpClient;

    @Inject
    @TaskExecutors.IO
    private ExecutorService ioExecutorService;

    /**
     * Validates the given URL link.
     *
     * @param url The URL to be validated.
     * @return A boolean indicating whether the URL is valid or not.
     */
    public CompletableFuture<Boolean> validateUrl(String url) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                JavaHttpClient javaHttpClient = JavaHttpClient.newBuilder()
                        .connectTimeout(Duration.ofSeconds(5))
                        .build();

                JavaHttpRequest javaHttpRequest = JavaHttpRequest.newBuilder()
                        .uri(URI.create(url))
                        .GET()
                        .build();

                JavaHttpResponse<String> response = javaHttpClient.send(javaHttpRequest, HttpResponse.BodyHandlers.ofString());

                if (response.statusCode() < 300) {
                    return true; // URL is valid if the HTTP status code is less than 300.
                } else {
                    return false; // URL is invalid if the HTTP status code is 300 or greater.
                }
            } catch (Exception e) {
                return false; // URL is invalid if an exception occurs.
            }
        }, ioExecutorService);
    }

    /**
     * Checks if the given URL is valid and returns an appropriate HttpResponse.
     *
     * @param request The HttpRequest containing the URL to be validated.
     * @return An HttpResponse indicating the validity of the URL.
     */
    public HttpResponse<String> checkUrlValidity(HttpRequest<String> request) {
        String url = request.getBody();
        try {
            boolean isValid = validateUrl(url).get(); // Blocking call for demonstration purposes.
            if (isValid) {
                return HttpResponse.ok("URL is valid.");
            } else {
                return HttpResponse.status(HttpStatus.NOT_FOUND, "URL is not valid.");
            }
        } catch (Exception e) {
            return HttpResponse.status(HttpStatus.INTERNAL_SERVER_ERROR, "Error validating URL.");
        }
    }
}
