// 代码生成时间: 2025-09-06 02:06:12
package com.example.processmanager;

import io.micronaut.context.annotation.Bean;
import io.micronaut.context.annotation.Factory;
import io.micronaut.context.annotation.Requires;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import javax.inject.Singleton;

/**
 * ProcessManager provides functionality to manage system processes.
 */
@Factory
@Requires(env = "processManagerEnabled")
public class ProcessManager {

    @Bean
    @Singleton
    public ProcessController processController() {
        return new ProcessController();
    }

    /**
     * A controller class for handling process management operations.
     */
    public static class ProcessController {

        /**
         * Execute a command in the system shell.
         *
         * @param command The command to execute.
         * @return The output of the command execution.
         */
        public String executeCommand(String command) {
            try {
                Process process = Runtime.getRuntime().exec(command);
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(process.getInputStream()));

                StringBuilder output = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    output.append(line).append("
");
                }

                int exitCode = process.waitFor();
                if (exitCode == 0) {
                    return output.toString();
                } else {
                    throw new RuntimeException("Command execution failed with exit code: " + exitCode);
                }
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException("Error executing command: " + command, e);
            }
        }
    }
}
