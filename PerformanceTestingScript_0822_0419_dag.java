// 代码生成时间: 2025-08-22 04:19:42
package com.example.performance;

import io.micronaut.core.async.annotation.SingleResult;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.reactivex.Flowable;
import java.util.concurrent.TimeUnit;

public class PerformanceTestingScript {

    @Client("/")
    private HttpClient client;

    public void executePerformanceTest() {
        try {
            // Define the number of requests and the delay between them
            int numberOfRequests = 100;
            long delayBetweenRequests = 100; // in milliseconds

            // Create a flowable that emits the number of requests
            Flowable<HttpRequest<?>> flowable = Flowable.range(1, numberOfRequests)
                .map(i -> HttpRequest.GET("/your/api/path"))
                .flatMap(client::toBlocking)
                .doOnNext(response -> System.out.println("Response status: " + response.status()));

            // Subscribe to the flowable and perform the requests
            flowable.subscribe();

            // Optionally, you can measure the time taken to perform the requests
            long startTime = System.nanoTime();
            flowable
                .timeout(1, TimeUnit.MINUTES) // Set a timeout for the entire flowable
                .blockingAwait();
            long endTime = System.nanoTime();

            System.out.println("Total time taken: " + TimeUnit.NANOSECONDS.toMillis(endTime - startTime) + " ms");
        } catch (Exception e) {
            // Handle any exceptions that occur during the execution of the performance test
            System.err.println("An error occurred during the performance test: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        PerformanceTestingScript script = new PerformanceTestingScript();
        script.executePerformanceTest();
    }
}
