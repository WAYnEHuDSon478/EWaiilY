// 代码生成时间: 2025-09-24 05:03:59
package com.example.demo;

import io.micronaut.scheduling.annotation.Scheduled;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Produces;
import javax.inject.Singleton;
import java.time.LocalDateTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Controller("/task")
@Singleton
public class ScheduledTaskController {

    // Executor service to manage scheduled tasks
    private final ExecutorService executor = Executors.newScheduledThreadPool(1);

    // This method is triggered by the Scheduled annotation and will execute every 5 seconds
    @Scheduled(fixedRate = "5s")
    public void executeScheduledTask() {
        try {
            // Log the current time when the task is executed
            System.out.println("Scheduled task executed at: " + LocalDateTime.now());
            // Add your task logic here
        } catch (Exception e) {
            // Handle any exceptions that may occur during task execution
            System.err.println("Error executing scheduled task: " + e.getMessage());
       }
    }

    // Endpoint to manually trigger the scheduled task
    @Get("/run")
    @Produces(MediaType.TEXT_PLAIN)
    public String runScheduledTaskManually() {
        try {
            // Execute the task immediately without waiting for the scheduled time
            executor.submit(this::executeScheduledTask).get();
            return "Scheduled task executed manually.";
        } catch (Exception e) {
            // Handle exceptions and return an error message
            return "Error executing scheduled task manually: " + e.getMessage();
       }
    }

    // Shutdown hook to properly shut down the executor service when the application closes
    public void close() throws Exception {
        executor.shutdown();
        executor.awaitTermination(60, TimeUnit.SECONDS);
    }
}
