// 代码生成时间: 2025-08-06 19:21:03
import io.micronaut.http.HttpRequest;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.scheduling.TaskExecutors;
import java.net.URL;
import javax.inject.Singleton;

/**
 * WebContentFetcher class is responsible for fetching content from a given URL.
 * It uses Micronaut's HttpClient to perform HTTP requests.
 */
@Singleton
public class WebContentFetcher {

    /**
     * HttpClient instance to perform HTTP requests.
     */
    @Client("/")
    private HttpClient httpClient;

    /**
     * Fetches content from the specified URL and returns it as a String.
     * 
     * @param url The URL to fetch content from.
     * @return The fetched content as a String.
     * @throws Exception If an error occurs during the fetching process.
     */
    public String fetchContent(String url) throws Exception {
        try {
            // Check if the URL is valid
            if (url == null || url.isEmpty()) {
                throw new IllegalArgumentException("URL cannot be null or empty");
            }

            // Make an HTTP GET request to the specified URL
            HttpRequest<String> request = HttpRequest.GET(url);
            String content = httpClient.toBlocking().retrieve(request);
            return content;
        } catch (Exception e) {
            // Handle any exceptions that occur during the fetching process
            throw new Exception("Failed to fetch content from URL: " + url, e);
        }
    }
}
