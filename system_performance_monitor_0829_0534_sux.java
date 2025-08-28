// 代码生成时间: 2025-08-29 05:34:11
package com.example.monitor;

import io.micronaut.core.annotation.Introspected;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.lang.management.RuntimeMXBean;
import java.time.Duration;
import java.time.Instant;

@Introspected
public class SystemPerformanceMonitor {

    private final OperatingSystemMXBean osBean;
    private final RuntimeMXBean runtimeBean;

    public SystemPerformanceMonitor() {
        this.osBean = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);
        this.runtimeBean = ManagementFactory.getRuntimeMXBean();
    }

    /**
     * Gets the CPU load average for the last minute.
     *
     * @return The load average, or -1 if not available.
     */
    public double getCpuLoadAverage() {
        return osBean.getSystemCpuLoad();
    }

    /**
     * Gets the total physical memory size.
     *
     * @return The total physical memory size in bytes.
     */
    public long getTotalPhysicalMemorySize() {
        return osBean.getTotalPhysicalMemorySize();
    }

    /**
     * Gets the free physical memory size.
     *
     * @return The free physical memory size in bytes.
     */
    public long getFreePhysicalMemorySize() {
        return osBean.getFreePhysicalMemorySize();
    }

    /**
     * Gets the system uptime.
     *
     * @return The system uptime in milliseconds.
     */
    public long getSystemUptime() {
        return runtimeBean.getUptime();
    }

    /**
     * Main method to run the system performance monitor tool.
     *
     * @param args The command line arguments.
     */
    public static void main(String[] args) {
        SystemPerformanceMonitor monitor = new SystemPerformanceMonitor();
        Instant start = Instant.now();
        try {
            while (true) {
                double cpuLoad = monitor.getCpuLoadAverage();
                long totalMemory = monitor.getTotalPhysicalMemorySize();
                long freeMemory = monitor.getFreePhysicalMemorySize();
                long uptime = monitor.getSystemUptime();

                System.out.println("CPU Load: " + cpuLoad + "%");
                System.out.println("Total Memory: " + totalMemory + " bytes");
                System.out.println("Free Memory: " + freeMemory + " bytes");
                System.out.println("System Uptime: " + Duration.ofMillis(uptime).toMinutes() + " minutes");

                Thread.sleep(5000); // Update every 5 seconds
            }
        } catch (InterruptedException e) {
            Instant end = Instant.now();
            long duration = Duration.between(start, end).toMillis();
            System.out.println("Monitoring interrupted. Total run time: " + duration + " ms");
        }
    }
}
