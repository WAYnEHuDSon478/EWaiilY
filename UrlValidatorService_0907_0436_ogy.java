// 代码生成时间: 2025-09-07 04:36:59
package com.example.urlvalidator;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import java.net.URI;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
@Controller("/api")
public class UrlValidatorService {

    @Inject
    private HttpClient httpClient;

    /**
     * Endpoint to validate a URL.
     * @param url The URL to validate.
     * @return HttpResponse with status code and message indicating the validity of the URL.
     */
    @Get("/validate", produces = MediaType.APPLICATION_JSON)
    public HttpResponse<String> validateUrl(String url) {
        try {
            // Validate URL format
            if (!isValidUrl(url)) {
                return HttpResponse.badRequest("Invalid URL format.");
            }

            // Try to make a HEAD request to the URL to check its validity
            httpClient.toBlocking().exchange HEAD(URI.create(url));
            return HttpResponse.ok("URL is valid.");
        } catch (HttpClientResponseException e) {
            // Handle errors related to HTTP response
            return HttpResponse.status(e.getStatus()).body("URL is not valid: " + e.getMessage());
        } catch (Exception e) {
            // Handle any other errors
            return HttpResponse.serverError("An error occurred while validating the URL: " + e.getMessage());
        }
    }

    /**
     * Checks if a given string is a valid URL.
     * @param urlString The URL string to check.
     * @return true if the URL is valid, false otherwise.
     */
    private boolean isValidUrl(String urlString) {
        try {
            new URI(urlString);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}