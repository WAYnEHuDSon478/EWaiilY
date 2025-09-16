// 代码生成时间: 2025-09-17 03:20:01
package com.example.demo;

import io.micronaut.scheduling.annotation.Scheduled;
import io.reactivex.disposables.Disposable;
import javax.inject.Singleton;
import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * ScheduledTaskScheduler provides scheduling functionality for tasks that need to be executed periodically.
 */
@Singleton
public class ScheduledTaskScheduler {

    private final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
    private Disposable taskDisposable;

    /**
     * Schedules a task to run at fixed intervals.
     *
     * @param initialDelay Initial delay before the task starts.
     * @param period Period after which the task is executed repeatedly.
     * @param task Task to be executed.
     */
    @Scheduled(fixedRate = "10s")
    public void scheduleTaskWithFixedRate(int initialDelay, int period, Runnable task) {
        try {
            if (taskDisposable != null && !taskDisposable.isDisposed()) {
                taskDisposable.dispose();
            }
            taskDisposable = executorService.scheduleAtFixedRate(task, initialDelay, period, TimeUnit.SECONDS);
        } catch (Exception e) {
            // Handle exceptions appropriately
            System.err.println("Error scheduling task: " + e.getMessage());
        }
    }

    /**
     * Executes a task at fixed intervals with a specific delay.
     *
     * @param delay Delay before the task starts.
     * @param period Period after which the task is executed repeatedly.
     * @param task Task to be executed.
     */
    public void executeTaskWithFixedRate(int delay, int period, Runnable task) {
        executorService.scheduleAtFixedRate(task, delay, period, TimeUnit.SECONDS);
    }

    /**
     * Stops the scheduled task.
     */
    public void stopScheduledTask() {
        if (taskDisposable != null && !taskDisposable.isDisposed()) {
            taskDisposable.dispose();
        }
    }

    /*
     * Example usage of the scheduled task scheduler.
     */
    @Scheduled(cron = "0 * * * * ?")
    public void exampleTask() {
        try {
            // Perform your task here
            System.out.println("Task executed at: " + LocalDateTime.now());
        } catch (Exception e) {
            // Handle exceptions appropriately
            System.err.println("Error executing task: " + e.getMessage());
        }
    }
}
