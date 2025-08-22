// 代码生成时间: 2025-08-22 15:58:08
import io.micronaut.context.annotation.Requires;
    import io.micronaut.http.HttpResponse;
    import io.micronaut.http.annotation.Controller;
    import io.micronaut.http.annotation.Get;
    import io.micronaut.http.exceptions.HttpStatusException;
    import io.micronaut.http.server.util.HttpServerUtil;
    import javax.inject.Singleton;
    import java.io.IOException;
    import java.net.HttpURLConnection;
    import java.net.URL;

    /**
     * A simple network status checker service that checks if a network connection is available.
     */
    @Controller("/status")
    @Singleton
    @Requires(property = "app.network.status.checker.enabled", value = "true")
    public class NetworkStatusChecker {

        /**
         * Endpoint to check the network status.
         *
         * @return HttpResponse with status code 200 if the network is reachable, otherwise 503.
         */
        @Get("/ping")
        public HttpResponse<String> checkNetworkStatus() {
            try {
                URL url = new URL("http://www.google.com"); // Using Google as a test endpoint
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(3000);
                connection.setReadTimeout(3000);

                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    return HttpResponse.ok("Network is reachable.");
                } else {
                    throw new HttpStatusException(HttpResponse.status(responseCode), "Network unreachable: HTTP status code " + responseCode);
                }
            } catch (IOException e) {
                throw new HttpStatusException(HttpResponse.status(503), "Network unreachable: IOException occurred", e);
            }
        }
    }