// 代码生成时间: 2025-09-14 01:46:11
 * @author: [Your Name]
 * @version: 1.0.0
 */

package com.example.processmanager;

import io.micronaut.core.annotation.Nullable;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.runtime.server.EmbeddedServer;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Controller("/processes")
public class ProcessManager {

    private final ConcurrentHashMap<String, ProcessWrapper> processes = new ConcurrentHashMap<>();
    private static final String PROCESS_STARTED = "Process started";
    private static final String PROCESS_NOT_FOUND = "Process not found";
    private static final String PROCESS_ALREADY_EXISTS = "Process already exists";
    private static final String PROCESS_STOPPED = "Process stopped";

    @Inject
    private EmbeddedServer server;

    // Starts a new process with a unique identifier
    @Get("/start/{processId}")
    public HttpResponse<String> startProcess(@PathVariable String processId) {
        if (processes.containsKey(processId)) {
            return HttpResponse.badRequest(PROCESS_ALREADY_EXISTS);
        }
        processes.put(processId, new ProcessWrapper(processId));
        return HttpResponse.ok(PROCESS_STARTED);
    }

    // Stops an existing process by its identifier
    @Get("/stop/{processId}")
    public HttpResponse<String> stopProcess(@PathVariable String processId) {
        if (!processes.containsKey(processId)) {
            return HttpResponse.badRequest(PROCESS_NOT_FOUND);
        }
        processes.remove(processId).stop();
        return HttpResponse.ok(PROCESS_STOPPED);
    }

    // Lists all running processes
    @Get("/list")
    public HttpResponse<List<String>> listProcesses() {
        List<String> processIds = new ArrayList<>(processes.keySet());
        return HttpResponse.ok(processIds);
    }

    private static class ProcessWrapper {
        private final String id;
        private volatile boolean running = true;

        public ProcessWrapper(String id) {
            this.id = id;
        }

        public void stop() {
            running = false;
        }

        public boolean isRunning() {
            return running;
        }

        public String getId() {
            return id;
        }
    }
}
