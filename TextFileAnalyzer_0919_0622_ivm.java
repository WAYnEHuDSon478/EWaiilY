// 代码生成时间: 2025-09-19 06:22:50
package com.example.textfileanalyzer;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import javax.annotation.Nullable;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Introspected
@Controller("/textfileanalyzer")
public class TextFileAnalyzer {

    private static final String PATH_TO_FILES = "./files/"; // Assuming files are stored in the 'files' directory

    // This method returns the content of the specified text file
    @Get("/{filename}")
    public HttpResponse<String> analyzeFileContent(@PathVariable String filename) {
        try {
            // Check if the file exists
            if (!Files.exists(Paths.get(PATH_TO_FILES + filename))) {
                return HttpResponse.notFound();
            }

            // Read the file content and return it
            String content = new BufferedReader(new FileReader(PATH_TO_FILES + filename)).lines()
                    .collect(Collectors.joining("
"));
            return HttpResponse.ok(content);
        } catch (IOException e) {
            // Log the exception and return a server error response
            // Logger.error("Error reading file: " + filename, e);
            return HttpResponse.serverError();
        }
    }

    // This method returns the number of lines in the specified text file
    @Get("/{filename}/lines")
    public HttpResponse<Integer> countLines(@PathVariable String filename) {
        try (Stream<String> stream = Files.lines(Paths.get(PATH_TO_FILES + filename))) {
            // Count the lines and return the count
            int lines = stream.count();
            return HttpResponse.ok(lines);
        } catch (IOException e) {
            // Log the exception and return a server error response
            // Logger.error("Error counting lines in file: " + filename, e);
            return HttpResponse.serverError();
        }
    }

    // This method returns a list of unique words from the specified text file
    @Get("/{filename}/words")
    public HttpResponse<List<String>> uniqueWords(@PathVariable String filename) {
        try (Stream<String> stream = Files.lines(Paths.get(PATH_TO_FILES + filename))) {
            // Split each line into words, filter out duplicates, and collect into a list
            List<String> uniqueWords = stream
                    .flatMap(line -> Arrays.stream(line.split("[ \w]+")))
                    .filter(word -> !word.isEmpty())
                    .distinct()
                    .collect(Collectors.toList());
            return HttpResponse.ok(uniqueWords);
        } catch (IOException e) {
            // Log the exception and return a server error response
            // Logger.error("Error getting unique words from file: " + filename, e);
            return HttpResponse.serverError();
        }
    }
}
