// 代码生成时间: 2025-09-14 12:28:38
import io.micronaut.core.type.Argument;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.uri.UriTemplate;
import io.micronaut.web.router.annotation.Get;
import javax.inject.Inject;
import java.net.URI;
import java.util.concurrent.CompletableFuture;

// 控制器类，负责处理HTTP请求
@Controller("/fetch")
public class WebContentFetcher {

    // 注入HttpClient用于发送HTTP请求
    @Inject
    @Client("{webClient.url}")
    private HttpClient httpClient;

    // 处理GET请求，用于接收网页内容抓取的URL
    @Get("/webContent")
    public CompletableFuture<String> fetchWebContent(String url) {
        try {
            // 使用UriTemplate来处理URL，确保正确传递参数
            URI uri = UriTemplate.of(url).expand().toURI();
            // 发送GET请求并返回响应体内容
            return httpClient.toBlocking().retrieve(HttpRequest.GET(uri));
        } catch (Exception e) {
            // 错误处理，返回错误信息
            return CompletableFuture.completedFuture("Error fetching content: " + e.getMessage());
        }
    }
}
