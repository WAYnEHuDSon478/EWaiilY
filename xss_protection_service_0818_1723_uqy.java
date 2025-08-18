// 代码生成时间: 2025-08-18 17:23:49
package com.example.security;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.security.filters.SecurityFilter;
import jakarta.inject.Singleton;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

// XSS Protection Service that sanitizes input to prevent XSS attacks.
@Singleton
public class XssProtectionService implements SecurityFilter {

    // Pattern to identify potential XSS attacks. This is a basic example and can be extended.
    private static final Pattern XSS_PATTERN = Pattern.compile("<|>|&|'|"|/|\"|=|;");

    @Override
    public Mono<MutableHttpResponse<?>> doFilter(HttpRequest<?> request, SecurityFilterChain chain) {
        try {
            // Sanitize the request body to prevent XSS attacks
            String sanitizedBody = sanitizeRequestBody(request.getBody(String.class).orElse(""));

            // Replace the original body with the sanitized one
            request = request.body(Mono.just(sanitizedBody), String.class);

            // Continue with the filter chain
            return chain.proceed(request);
        } catch (Exception e) {
            // Handle any exceptions that occur during the sanitization process
            return Mono.just(createErrorResponse(e));
        }
    }

    // Sanitize the request body to remove any potential XSS threats
    private String sanitizeRequestBody(String requestBody) {
        if (requestBody == null || requestBody.isEmpty()) {
            return requestBody;
        }

        // Use regex to find and replace potential XSS threats
        Matcher matcher = XSS_PATTERN.matcher(requestBody);
        StringBuffer sanitized = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sanitized, "");
        }
        matcher.appendTail(sanitized);

        return sanitized.toString();
    }

    // Create an error response with a 400 status code for invalid requests
    private MutableHttpResponse<String> createErrorResponse(Exception e) {
        String errorMessage = "Invalid request: " + e.getMessage();
        return MutableHttpResponse.badRequest(errorMessage);
    }
}
