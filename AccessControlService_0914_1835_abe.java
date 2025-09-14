// 代码生成时间: 2025-09-14 18:35:04
package com.example.demo.service;

import io.micronaut.context.annotation.Requires;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import io.micronaut.security.token.jwt.render.JwtRoleReader;

import javax.inject.Singleton;
import java.util.Optional;

// 访问权限控制服务类
@Singleton
@Requires(property = "security.enabled", value = "true")
@Controller("/access")
public class AccessControlService {

    // 权限检查方法
    @Secured(SecurityRule.IS_AUTHENTICATED)
    @Get("/restricted")
    public String restrictedAccess() {
        return "This is a restricted access area.";
    }

    // 角色检查方法
    @Secured(SecurityRule.IS_AUTHENTICATED)
    @Get("/admin")
    public String adminAccess(HttpRequest<?> request, JwtRoleReader roleReader) {
        Optional<String> role = roleReader.getRoles();
        if (role.isPresent() && role.get().equals("ADMIN")) {
            return "This is an admin area.";
        } else {
            throw new AccessDeniedException("Access Denied: User is not an admin.");
        }
    }

    // 自定义异常类
    public static class AccessDeniedException extends RuntimeException {
        public AccessDeniedException(String message) {
            super(message);
        }
    }
}