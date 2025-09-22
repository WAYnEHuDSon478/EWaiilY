// 代码生成时间: 2025-09-22 14:53:29
package com.example.demo.service;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import javax.inject.Singleton;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Optional;

@Controller("/login")
@Secured(SecurityRule.IS_AUTHENTICATED)
@Singleton
public class LoginService {

    private final UserRepository userRepository;

    // Constructor injection of UserRepository
    public LoginService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Login endpoint
    @Post("/")
    public String login(@Valid @NotNull LoginRequest loginRequest) {
        try {
            // Validate credentials and find user
            Optional<User> userOptional = userRepository.findByEmail(loginRequest.getEmail());
            userOptional.ifPresentOrElse(
                user -> {
                    if (user.getPassword().equals(loginRequest.getPassword())) {
                        return "User authenticated";
                    } else {
                        throw new IllegalArgumentException("Invalid password");
                    }
                }, () -> {
                    throw new IllegalArgumentException("User not found");
                }
            );
        } catch (IllegalArgumentException e) {
            // Handle authentication exceptions
            return "Authentication failed: " + e.getMessage();
        }
    }

    // Request DTO
    public static class LoginRequest {
        @NotNull
        private String email;
        @NotNull
        private String password;

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}

// UserRepository.java
package com.example.demo.repository;

import java.util.Optional;

public interface UserRepository {
    // Method to find a user by email
    Optional<User> findByEmail(String email);
}

// User.java
package com.example.demo.model;

public class User {
    private String email;
    private String password;
    // Other fields and methods

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}