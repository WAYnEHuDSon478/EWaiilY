// 代码生成时间: 2025-08-03 12:35:20
package com.example.auth;

import io.micronaut.context.annotation.Bean;
import io.micronaut.context.annotation.Factory;
import io.micronaut.security.authentication.AuthenticationProvider;
import io.micronaut.security.authentication.UserDetails;
import io.micronaut.security.config.SecurityConfiguration;
import io.micronaut.security.rules.SecurityRule;
import io.micronaut.security.token.jwt.render.JwtTokenRenderer;
import io.micronaut.security.token.jwt.signature.JwksSignatureVerifier;
import io.micronaut.security.token.jwt.signature.SignatureVerificationException;
import javax.inject.Singleton;
import java.util.Optional;

@Factory
public class UserAuthenticationService {

    @Bean
    @Singleton
    public AuthenticationProvider authenticationProvider() {
        return new UserDetailsAuthenticationProvider();
    }

    @Bean
    @Singleton
    public JwksSignatureVerifier jwksSignatureVerifier() {
        return new JwksSignatureVerifier() {
            @Override
            public String verifySignature(String token, String keyId) {
                // Implement your own signature verification logic here
                // For example, talking to a remote JWKS endpoint
                return "secret";
            }
        };
    }

    private static class UserDetailsAuthenticationProvider implements AuthenticationProvider {

        @Override
        public UserDetails loadUserByUsername(String username, Iterable<GrantedAuthority> authorities) {
            // Implement your own user lookup logic here
            // For example, querying a database
            UserDetails user = new UserDetails(username);
            user.addAuthorities(new SimpleGrantedAuthority("ROLE_USER"));
            return user;
        }

        @Override
        public boolean supports(SecurityRule rule) {
            // Return true if this provider can handle the given security rule
            return rule.getName().equals(SecurityConfiguration.DEFAULT_ROLE);
        }
    }

    private static class SimpleGrantedAuthority implements GrantedAuthority {

        private final String authority;

        public SimpleGrantedAuthority(String authority) {
            this.authority = authority;
        }

        @Override
        public String getAuthority() {
            return authority;
        }
    }
}
