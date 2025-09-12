// 代码生成时间: 2025-09-12 19:20:22
package com.example.security;

import io.micronaut.context.annotation.Value;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Status;
import io.micronaut.validation.validator.constraints.EmailValidator;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import io.micronaut.security.token.jwt.render.JwtTokenResponse;
import jakarta.validation.constraints.NotNull;
import java.util.List;

@Controller("/login")
public class LoginService {

    // Example of configuration property injection
    @Value("\${app.secret}")
    private String secret;

    // Simulated user database
    private final UserDatabase userDatabase;

    public LoginService(UserDatabase userDatabase) {
        this.userDatabase = userDatabase;
    }

    // POST endpoint for login
    @Secured(SecurityRule.IS_ANONYMOUS)
    @Post("/")
    @Status(io.micronaut.http.HttpStatus.OK)
    public JwtTokenResponse login(HttpRequest request, @NotNull @EmailValidator String email, @NotNull String password) {
        try {
            // Find user in the database
            User user = userDatabase.findUserByEmail(email);
            if (user == null) {
                throw new IllegalArgumentException("User not found");
            }

            if (!user.getPassword().equals(password)) {
                throw new IllegalArgumentException("Invalid password");
            }

            // Generate token using the user's role
            return JwtTokenResponse.builder()
                    .token(genToken(user))
                    .build();
        } catch (IllegalArgumentException e) {
            // Handle authentication failure
            throw new AuthenticationException(e.getMessage());
        }
    }

    // Generate JWT token for a user
    private String genToken(User user) {
        // Simulate token generation
        return "Token:" + user.getEmail() + ":" + user.getRole();
    }
}

class User {
    private String email;
    private String password;
    private String role;

    public User(String email, String password, String role) {
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }
}

class UserDatabase {
    // Simulated database lookup
    public User findUserByEmail(String email) {
        // This would be replaced with actual database logic
        if ("admin@example.com".equals(email)) {
            return new User(email, "admin123", "ADMIN");
        }
        return null;
    }
}

class AuthenticationException extends RuntimeException {
    public AuthenticationException(String message) {
        super(message);
    }
}