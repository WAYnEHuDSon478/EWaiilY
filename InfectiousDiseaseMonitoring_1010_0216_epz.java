// 代码生成时间: 2025-10-10 02:16:24
package com.example.monitoring;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.HttpResponse;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

@Controller("/api")
public class InfectiousDiseaseMonitoring {

    private final InfectiousDiseaseService diseaseService;

    // Constructor injection of the service
    public InfectiousDiseaseMonitoring(InfectiousDiseaseService diseaseService) {
        this.diseaseService = diseaseService;
    }

    // Endpoint to retrieve the latest disease statistics
    @Get("/diseaseStatistics")
    public HttpResponse<Map<String, Object>> getDiseaseStatistics(
            @NotNull String diseaseName) {
        try {
            Map<String, Object> statistics = diseaseService.getStatisticsByName(diseaseName);
            return HttpResponse.ok(statistics);
        } catch (IllegalArgumentException e) {
            return HttpResponse.badRequest(e.getMessage());
        }
    }
}

/*
 * Service class responsible for interacting with the data layer to retrieve disease statistics.
 */
package com.example.monitoring;

import java.util.HashMap;
import java.util.Map;

public class InfectiousDiseaseService {

    private static final Map<String, Map<String, Object>> diseaseData = new HashMap<>();

    static {
        // Sample data population
        diseaseData.put("COVID-19", new HashMap<>() {{
            put("cases", 100000);
            put("recoveries", 50000);
            put("deaths", 5000);
        }});
    }

    public Map<String, Object> getStatisticsByName(String diseaseName) {
        if (!diseaseData.containsKey(diseaseName)) {
            throw new IllegalArgumentException("Disease not found: " + diseaseName);
        }
        return diseaseData.get(diseaseName);
    }
}
