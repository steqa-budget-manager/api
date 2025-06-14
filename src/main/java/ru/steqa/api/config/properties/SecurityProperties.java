package ru.steqa.api.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Setter
@Getter
@Component
@ConfigurationProperties(prefix = "security")
public class SecurityProperties {
    private String[] allowedOrigins;
    private List<String> publicPaths;
    private JwtProperties jwt;

    @Setter
    @Getter
    public static class JwtProperties {
        private String secret;
        private ExpirationProperties access;
        private ExpirationProperties refresh;

        @Setter
        @Getter
        public static class ExpirationProperties {
            private long expirationMs;
        }
    }
}
