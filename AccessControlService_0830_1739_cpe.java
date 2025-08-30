// 代码生成时间: 2025-08-30 17:39:02
package com.example.accesscontrol;

import io.micronaut.context.annotation.Requires;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import javax.inject.Singleton;
import java.util.Collections;
import java.util.List;

/**
 * Access control service using Micronaut Security.
 * This controller demonstrates how to use role-based access control in Micronaut.
 */
@Requires(property = "accesscontrol.enabled")
@Singleton
@Controller("/access")
public class AccessControlService {

    // Define a method to check if the user has admin access
    @Get("/admin")
    @Secured(SecurityRule.IS_AUTHENTICATED) // Secured with any authenticated user
    public String adminAccess() {
        return "Admin access granted.";
    }

    // Define a method to check if the user has user access
    @Get("/user")
    @Secured(SecurityRule.IS_AUTHENTICATED) // Secured with any authenticated user
    public String userAccess() {
        return "User access granted.";
    }

    // Define a method to check if the user has guest access
    @Get("/guest")
    public String guestAccess() {
        return "Guest access granted.";
    }

    // Define a method to handle unauthorized access
    @Get("/unauthorized")
    @Secured(SecurityRule.IS_ANONYMOUS) // Secured for anonymous users
    public String unauthorizedAccess() {
        throw new AccessControlException("Access Denied: You do not have permission to access this resource.");
    }

    // Custom exception for access control
    public static class AccessControlException extends RuntimeException {
        public AccessControlException(String message) {
            super(message);
        }
    }
}
