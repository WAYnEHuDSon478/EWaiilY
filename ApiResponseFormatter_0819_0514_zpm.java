// 代码生成时间: 2025-08-19 05:14:13
package com.example.api;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.exceptions.HttpStatusException;
import io.micronaut.http.hateoas.JsonError;
import io.micronaut.http.server.exceptions.ExceptionHandler;
import io.micronaut.http.server.netty.errors.ExceptionHandlerRegistry;
import io.micronaut.runtime.Micronaut;
import io.micronaut.runtime.server.EmbeddedServer;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.Async;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.concurrent.ExecutorService;

@Controller("/api")
@Produces("application/json")
@Singleton
public class ApiResponseFormatter {
    private static final Logger logger = LoggerFactory.getLogger(ApiResponseFormatter.class);
    private final ExecutorService executorService;

    @Inject
    public ApiResponseFormatter(@TaskExecutors(TaskExecutors.IO) ExecutorService executorService) {
        this.executorService = executorService;
    }

    @Get("/format")
    public HttpResponse<String> formatResponse() {
        try {
            executorService.submit(() -> {
                // Simulate a long-running task
                try {
                    Thread.sleep(2000); // Simulate task delay
                } catch (InterruptedException e) {
                    logger.error("Task interrupted", e);
                }
            });
            // Return a formatted response
            return HttpResponse.<String>ok()
                    .header("X-Custom-Header", "CustomValue")
                    .body("{\"message\":\"Response successfully formatted\"}