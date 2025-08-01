// 代码生成时间: 2025-08-02 05:13:30
 * proper comments, and documentation to ensure maintainability and scalability.
 */

package com.example.auth;

import io.micronaut.context.annotation.Bean;
import io.micronaut.context.annotation.Factory;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.server.exceptions.ExceptionHandler;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;import io.micronaut.security.rules.SecurityRuleResult;
import io.micronaut.security.rules.SecurityRuleResultBuilder;
import io.micronaut.security.token.jwt.render.JwtTokenFactory;
import io.micronaut.security.token.jwt.render.JwtTokenRenderer;import io.reactivex.Single;
import javax.inject.Singleton;
import java.util.HashMap;
import java.util.Map;

@Controller("/login")
@Secured(SecurityRule.IS_ANONYMOUS)
public class UserLoginController {
    private final UserRepository userRepository;
    private final JwtTokenRenderer jwtTokenRenderer;
    private final JwtTokenFactory jwtTokenFactory;

    public UserLoginController(UserRepository userRepository, JwtTokenRenderer jwtTokenRenderer, JwtTokenFactory jwtTokenFactory) {
        this.userRepository = userRepository;
        this.jwtTokenRenderer = jwtTokenRenderer;
        this.jwtTokenFactory = jwtTokenFactory;
    }

    @Post("/authenticate")
    public Single<LoginResponse> login(HttpRequest<?> request, LoginCommand command) {
        User user = userRepository.findByUsername(command.getUsername());
        if (user == null || !user.getPassword().equals(command.getPassword())) {
            return Single.error(new UsernameNotFoundException("User not found or password is incorrect"));
        }

        return Single.just(jwtTokenRenderer.renderToken(jwtTokenFactory.generateToken(user)));
    }
}

@Factory
class UserLoginFactory {
    @Bean
    UserRepository userRepository() {
        // Provide a UserRepository instance here
        return new UserRepository();
    }
}

@Singleton
class UserRepository {
    // Simulated database for demonstration purposes
    private Map<String, User> users = new HashMap<>();

    public UserRepository() {
        users.put("user", new User("user", "password"));
    }

    public User findByUsername(String username) {
        return users.get(username);
    }
}

class User {
    private final String username;
    private final String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}

class LoginCommand {
    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

class LoginResponse {
    private String token;

    public LoginResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}

class UsernameNotFoundException extends RuntimeException {
    public UsernameNotFoundException(String message) {
        super(message);
    }
}

@ExceptionHandler(UsernameNotFoundException.class)
@Singleton
class UsernameNotFoundExceptionHandler implements ExceptionHandler<UsernameNotFoundException, HttpResponse<?>> {
    public HttpResponse<?> handle(UsernameNotFoundException exception) {
        return HttpResponse.status(HttpStatus.UNAUTHORIZED, "User not found or password is incorrect");
    }
}
