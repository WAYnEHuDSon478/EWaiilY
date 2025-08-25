// 代码生成时间: 2025-08-25 17:36:59
package com.example.api;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.MediaType;
import io.micronaut.core.type.Argument;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nullable;

@Controller("/api")
public class ApiResponseFormatter {
    
    private static final String SUCCESS_STATUS = "success";
    private static final String ERROR_STATUS = "error";
    private static final String DATA_KEY = "data";
    private static final String MESSAGE_KEY = "message";
    private static final String STATUS_KEY = "status";

    @Get("/formatResponse")
    @Produces(MediaType.APPLICATION_JSON)
    public HttpResponse<Map<String, Object>> formatResponse() {
        Map<String, Object> response = new HashMap<>();
        response.put(STATUS_KEY, SUCCESS_STATUS);
        response.put(MESSAGE_KEY, "Formatted response successfully");
        response.put(DATA_KEY, Collections.emptyMap());

        return HttpResponse.ok(response);
    }

    /**
     * Construct a success response with the provided data.
     *
     * @param data the data to include in the response
     * @return a formatted API response
     */
    public HttpResponse<Map<String, Object>> successResponse(@Nullable Object data) {
        Map<String, Object> response = new HashMap<>();
        response.put(STATUS_KEY, SUCCESS_STATUS);
        response.put(MESSAGE_KEY, "Success");
        if (data != null) {
            response.put(DATA_KEY, data);
        }
        return HttpResponse.ok(response);
    }

    /**
     * Construct an error response with the provided message.
     *
     * @param message the error message to include in the response
     * @return a formatted API response
     */
    public HttpResponse<Map<String, Object>> errorResponse(String message) {
        Map<String, Object> response = new HashMap<>();
        response.put(STATUS_KEY, ERROR_STATUS);
        response.put(MESSAGE_KEY, message);
        return HttpResponse.badRequest(response);
    }

    /**
     * Construct an error response with the provided message and status code.
     *
     * @param message the error message to include in the response
     * @param statusCode the HTTP status code to use
     * @return a formatted API response
     */
    public HttpResponse<Map<String, Object>> errorResponse(String message, int statusCode) {
        Map<String, Object> response = new HashMap<>();
        response.put(STATUS_KEY, ERROR_STATUS);
        response.put(MESSAGE_KEY, message);
        return HttpResponse.status(statusCode, response);
    }
}
