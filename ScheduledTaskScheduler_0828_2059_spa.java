// 代码生成时间: 2025-08-28 20:59:38
package com.example.demo.tasks;

import io.micronaut.scheduling.annotation.Scheduled;
import javax.inject.Singleton;
import java.util.concurrent.TimeUnit;

/**
 * A simple scheduled task scheduler using Micronaut's @Scheduled annotation.
 * This class contains a method that will be executed on a fixed rate schedule.
 */
@Singleton
public class ScheduledTaskScheduler {

    /**
     * Executes the scheduled task on a fixed rate delay.
     * The task will be performed every 5 seconds.
     */
    @Scheduled(fixedRate = "5s")
    public void executeTask() {
        try {
            // Your task logic here
            System.out.println("Scheduled task executed at: " + java.time.LocalDateTime.now());

            // You can add any business logic here that needs to be executed periodically.
            // For example, you might want to perform a database operation,
            // send a notification, or perform some cleanup tasks.

        } catch (Exception e) {
            // Handle any exceptions that occur during task execution
            System.err.println("An error occurred while executing the scheduled task: " + e.getMessage());
        }
    }
}
