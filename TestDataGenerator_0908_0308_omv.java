// 代码生成时间: 2025-09-08 03:08:57
import io.micronaut.core.annotation.Introspected;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Produces;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Test Data Generator Controller
 *
 * This controller is responsible for generating random test data.
 */
@Controller("/test-data")
@Introspected
public class TestDataGenerator {

    /**
     * Endpoint to generate a simple test data map.
     *
     * @return A map containing randomly generated data.
     */
    @Get("/generate")
    @Produces(MediaType.APPLICATION_JSON)
    public HttpResponse<Map<String, String>> generateTestData() {
        Map<String, String> testData = new HashMap<>();
        try {
            // Generate random data
            String randomId = UUID.randomUUID().toString();
            String randomName = "TestName" + randomId;
            String randomEmail = randomName + "@example.com";

            // Populate the map with random data
            testData.put("id", randomId);
            testData.put("name", randomName);
            testData.put("email", randomEmail);

            // Return the test data
            return HttpResponse.ok(testData);
        } catch (Exception e) {
            // Handle any exceptions that may occur
            return HttpResponse.serverError(e.getMessage());
        }
    }
}
