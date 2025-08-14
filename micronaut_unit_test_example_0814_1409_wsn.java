// 代码生成时间: 2025-08-14 14:09:15
package com.example.demo;

import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import javax.inject.Inject;

// Define a simple service class to demonstrate unit testing
class GreetingService {
    public String sayHello(String name) {
        return "Hello, " + name + "!";
    }
}

// Unit test class for the GreetingService
@MicronautTest // Indicates that this is a Micronaut test
class GreetingServiceTest {

    @Inject
    GreetingService greetingService; // The service to be tested

    @Test
    void testSayHello() {
        // Arrange
        String expected = "Hello, World!";
        String name = "World";

        // Act
        String actual = greetingService.sayHello(name);

        // Assert
        Assertions.assertEquals(expected, actual, "The greeting does not match the expected value.");
    }
}
