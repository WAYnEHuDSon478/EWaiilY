// 代码生成时间: 2025-08-15 10:57:22
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.runtime.Micronaut;
import io.micronaut.context.annotation.Bean;
import io.micronaut.context.annotation.Factory;
import io.micronaut.http.client.exceptions.HttpClientException;
import io.micronaut.core.annotation.Introspected;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.net.URI;
import java.net.URLEncoder;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

// WebContentScraper class
public class WebContentScraper {

    private HttpClient client;
    private ExecutorService executorService;

    // Constructor
    public WebContentScraper(HttpClient client, ExecutorService executorService) {
        this.client = client;
        this.executorService = executorService;
    }

    // Method to scrape content from a webpage
    public Single<String> scrapeContent(String url) {
        return Single.fromCallable(() -> {
            try {
                HttpResponse<String> response = client.toBlocking().exchange(HttpRequest.GET(URI.create(url)), String.class);
                if (response.getStatus().getCode() == 200) {
                    return response.getBody().get();
                } else {
                    throw new HttpClientException("Failed to scrape content: HTTP status code " + response.getStatus().getCode());
                }
            } catch (Exception e) {
                throw new RuntimeException("Error scraping content from URL: " + url, e);
            }
        }).subscribeOn(Schedulers.from(executorService));
    }

    // Main method to run the scraper
    public static void main(String[] args) throws InterruptedException {
        Disposable disposable = new WebContentScraper(httpClient(), executorService())
                .scrapeContent("https://example.com")
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String content) throws Exception {
                        System.out.println("Scraped content: " + content);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        System.err.println("Error scraping content: " + throwable.getMessage());
                    }
                });

        executorService().shutdown();
        executorService().awaitTermination(5, TimeUnit.MINUTES);
        disposable.dispose();
    }

    // Factory method to create HttpClient bean
    @Bean
    @Factory
    public HttpClient httpClient() {
        return HttpClient.newBuilder()
                .build();
    }

    // Factory method to create ExecutorService bean
    @Bean
    @Factory
    public ExecutorService executorService() {
        return Executors.newFixedThreadPool(10);
    }
}
