// 代码生成时间: 2025-09-07 17:55:27
package com.example.logparser;

import io.micronaut.context.annotation.Requires;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import javax.inject.Singleton;

/**
 * Service class for parsing log files.
 */
@Singleton
@Controller("/logparser")
@Requires(beans = LogParserService.class)
public class LogParserService {

    /**
     * Method to parse and return log lines containing the specified keyword.
     * 
     * @param filePath The path to the log file.
     * @param keyword The keyword to search for in the log.
     * @return A list of log lines containing the keyword.
     */
    @Get("/{filePath}/{keyword}")
    public HttpResponse<List<String>> parseLogFile(
            @PathVariable String filePath,
            @PathVariable String keyword) {
        try {
            // Read all lines from the file into a list
            List<String> lines = Files.readAllLines(Paths.get(filePath));

            // Filter lines containing the keyword
            List<String> filteredLines = lines.stream()
                    .filter(line -> line.contains(keyword))
                    .collect(Collectors.toList());

            // Return the list of filtered lines
            return HttpResponse.ok(filteredLines);
        } catch (IOException e) {
            // Handle file reading error
            return HttpResponse.serverError();
        } catch (Exception e) {
            // Handle other general exceptions
            return HttpResponse.badRequest(e.getMessage());
        }
    }
}
