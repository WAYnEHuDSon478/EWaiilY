// 代码生成时间: 2025-08-28 13:57:59
package com.example.demo;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.exceptions.ExceptionHandler;
import io.micronaut.http.server.exceptions.InternalServerException;
import io.micronaut.http.hateoas.JsonError;
import io.micronaut.http.exceptions.HttpStatusException;
import io.micronaut.core.type.Argument;

@Controller("/api")
public class ApiController {

    // A simple GET endpoint that returns a welcome message
    @Get("/hello")
    public String sayHello() {
        return "Hello, welcome to the RESTful API!";
    }

    // A GET endpoint that returns a message with a name parameter
    @Get("/greet/{name}")
    public String greet(@PathVariable String name) {
        return "Hello, " + name + "!";
    }

    // Exception handler to return a JSON error when an internal server error occurs
    @ExceptionHandler(InternalServerException.class)
    public JsonError handleInternalServerException(InternalServerException e) {
        return JsonError.of(
                "Internal Server Error",
                "An unexpected error occurred: " + e.getMessage(),
                Argument.STRING,
                Argument.STRING
        );
    }

    // Exception handler to return a custom JSON error for other HTTP status exceptions
    @ExceptionHandler(HttpStatusException.class)
    public JsonError handleHttpStatusException(HttpStatusException e) {
        return JsonError.of(
                e.getStatus().getReason(),
                e.getMessage(),
                Argument.STRING,
                Argument.STRING
        );
    }
}
