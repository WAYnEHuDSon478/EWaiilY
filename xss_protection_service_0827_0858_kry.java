// 代码生成时间: 2025-08-27 08:58:29
package com.example.security;

import io.micronaut.context.annotation.Value;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.http.annotation.Filter;
import io.micronaut.http.filter.HttpServerFilter;
import io.micronaut.http.filter.ServerFilterChain;
import jakarta.inject.Singleton;
import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;
import org.jsoup.safety.Whitelist;

@Singleton
@Filter("/**")
public class XssProtectionService implements HttpServerFilter {
    
    @Value('${xss.filter.enabled}')
    private boolean xssFilterEnabled;
    
    @Override
    public MutableHttpResponse<?> doFilter(HttpRequest<?> request, ServerFilterChain chain) {
        if (!xssFilterEnabled) {
            // If XSS filter is disabled, pass through without sanitizing
            return chain.proceed(request);
        }
        
        // Sanitize request before proceeding
        String sanitizedBody = sanitize(request.getBody(String.class).orElse(""));
        
        // Continue the filter chain with the sanitized request body
        return chain.proceed(request.getBody(sanitizedBody));
    }
    
    /**
     * Sanitize the input string to prevent XSS attacks.
     * 
     * @param input The input string to sanitize.
     * @return The sanitized input string.
     */
    private String sanitize(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        
        // Use Jsoup's Whitelist to sanitize the input
        // You can customize the whitelist as per your requirements
        Safelist safelist = Whitelist.none()
                .addTags("p", "b", "i", "em", "strong", "a", "ul", "ol", "li", "br", "hr")
                .addAttributes("a