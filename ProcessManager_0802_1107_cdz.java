// 代码生成时间: 2025-08-02 11:07:04
package com.example.processmanager;

import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.Execution;
import io.reactivex.Maybe;
import javax.inject.Singleton;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Singleton
public class ProcessManager {

    private final ExecutorService executorService;

    public ProcessManager() {
        // Initialize the executor service with a fixed thread pool size
        executorService = Executors.newFixedThreadPool(10);
    }

    /**
     * Lists all current system processes.
     *
     * @return A list of process information.
     */
    public List<ProcessInfo> listProcesses() {
        try {
            // Retrieve the list of processes from the operating system
            return ProcessHandle.getAllProcessInfo().stream()
                    .map(ph -> new ProcessInfo(ph.pid(), ph.info()))
                    .toList();
        } catch (Exception e) {
            // Handle any errors that occur while listing processes
            throw new RuntimeException("Failed to list processes", e);
        }
    }

    /**
     * Starts a new system process.
     *
     * @param command The command to execute.
     * @return The ProcessInfo of the started process.
     */
    public ProcessInfo startProcess(String command) {
        try {
            // Start a new process using the provided command
            Process process = Runtime.getRuntime().exec(command);
            ProcessHandle handle = process.toHandle();
            return new ProcessInfo(handle.pid(), handle.info());
        } catch (Exception e) {
            // Handle any errors that occur while starting a process
            throw new RuntimeException("Failed to start process", e);
        }
    }

    /**
     * Stops a system process by its ID.
     *
     * @param pid The ID of the process to stop.
     * @return A boolean indicating whether the process was stopped successfully.
     */
    public boolean stopProcess(long pid) {
        try {
            // Get the process handle for the specified process ID
            ProcessHandle handle = ProcessHandle.of(pid).orElse(null);
            if (handle != null) {
                // Destroy the process
                return handle.destroy();
            } else {
                throw new RuntimeException("Process not found with ID: " + pid);
            }
        } catch (Exception e) {
            // Handle any errors that occur while stopping a process
            throw new RuntimeException("Failed to stop process", e);
        }
    }

    /**
     * Holds information about a system process.
     */
    public static class ProcessInfo {
        private final long pid;
        private final String command;
        private final long startTime;

        public ProcessInfo(long pid, ProcessHandle.Info info) {
            this.pid = pid;
            this.command = info.command().orElse("Unknown");
            this.startTime = info.startInstant().toEpochMilli();
        }

        public long getPid() {
            return pid;
        }

        public String getCommand() {
            return command;
        }

        public long getStartTime() {
            return startTime;
        }
    }
}
