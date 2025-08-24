// 代码生成时间: 2025-08-25 03:06:51
 * maintainability, and extensibility.
 *
 * @author Your Name
 * @version 1.0
 */

package com.example.testreport;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.MediaType;
import io.reactivex.Single;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import javax.inject.Singleton;

@Controller("/test")
@Singleton
public class TestReportGenerator {

    private static final String REPORTS_DIRECTORY = "reports";
    private static final String REPORT_FILE_EXTENSION = ".txt";

    /*
     * Generate and retrieve a test report.
     *
     * @return The content of the test report as a Single<String> object.
     * @throws IOException If an I/O error occurs reading from the file system.
     */
    @Get("/report")
    @Single
    public Single<String> generateTestReport() {
        try {
            // List all report files in the reports directory
            List<String> reportFileNames = Files.list(Paths.get(REPORTS_DIRECTORY))
                    .map(path -> path.getFileName().toString())
                    .filter(name -> name.endsWith(REPORT_FILE_EXTENSION))
                    .collect(Collectors.toList());

            // Build the report content by concatenating the contents of all report files
            String reportContent = reportFileNames.stream()
                    .map(name -> {
                        try {
                            return new String(Files.readAllBytes(Paths.get(REPORTS_DIRECTORY, name))) + "

";
                        } catch (IOException e) {
                            throw new RuntimeException("Failed to read report file: " + name, e);
                        }
                    })
                    .collect(Collectors.joining());

            return Single.just(reportContent);
        } catch (IOException e) {
            // Handle I/O exceptions and return an error message
            return Single.error(new RuntimeException("Failed to generate test report: " + e.getMessage(), e));
        }
    }
}
