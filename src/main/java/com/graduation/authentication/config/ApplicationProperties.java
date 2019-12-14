package com.graduation.authentication.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "authentication-properties")
public class ApplicationProperties {

    private final Security security = new Security();

    @Data
    public static class Security {
        private final Authentication authentication = new Authentication();

        @Data
        public static class Authentication {
            private final Jwt jwt = new Jwt();

            @Data
            public static class Jwt {
                private String secret;
                private long tokenValidityInSeconds = 14400;
            }
        }
    }
}
