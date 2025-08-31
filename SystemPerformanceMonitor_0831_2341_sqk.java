// 代码生成时间: 2025-08-31 23:41:32
package com.example.monitor;

import io.micronaut.context.annotation.Requires;
import io.micronaut.management.endpoint.annotation.Endpoint;
import io.micronaut.management.endpoint.annotation.Read;
import javax.inject.Singleton;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.util.HashMap;
import java.util.Map;

@Requires(property = 'endpoints.system-performance.enabled')
@Singleton
@Endpoint(id = "system-performance", defaultSensitive = true)
public class SystemPerformanceMonitor {

    private final OperatingSystemMXBean osBean;

    public SystemPerformanceMonitor() {
        this.osBean = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);
    }

    /**
     * Returns a map of system performance metrics.
     *
     * @return A map containing CPU, memory, and disk usage.
     */
    @Read
    public Map<String, Object> getSystemPerformanceMetrics() {
        Map<String, Object> metrics = new HashMap<>();

        try {
            double cpuLoad = osBean.getSystemCpuLoad();
            metrics.put("cpuLoad", cpuLoad);

            long totalMemory = osBean.getTotalPhysicalMemorySize();
            long freeMemory = osBean.getFreePhysicalMemorySize();
            metrics.put("totalMemory", totalMemory);
            metrics.put("freeMemory", freeMemory);

            Runtime runtime = Runtime.getRuntime();
            long usedMemory = runtime.totalMemory() - runtime.freeMemory();
            metrics.put("usedMemory", usedMemory);

            long maxMemory = runtime.maxMemory();
            metrics.put("maxMemory", maxMemory);

            long usableSpace = getUsableSpace();
            metrics.put("usableSpace", usableSpace);

        } catch (Exception e) {
            // Handle exceptions and log errors if necessary
            metrics.put("error", "Failed to retrieve system performance metrics: " + e.getMessage());
        }

        return metrics;
    }

    /**
     * Returns the usable space of the file system.
     *
     * @return The usable space in bytes.
     */
    private long getUsableSpace() {
        long usableSpace = 0;
        try {
            File[] roots = File.listRoots();
            for (File root : roots) {
                FileUsage usage = root.getUsableSpace();
                if (usage != null) {
                    usableSpace += usage.getUsed();
                }
            }
        } catch (Exception e) {
            // Handle exceptions and log errors if necessary
            // Log error message
        }
        return usableSpace;
    }
}
