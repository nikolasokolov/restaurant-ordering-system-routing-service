package com.graduation.authentication.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.validation.constraints.NotNull;

@Data
@Configuration
@ConfigurationProperties(prefix = "portal-service")
public class ApplicationProperties {

    private final Security security = new Security();

    @Data
    public static class Security {

        private final Rememberme rememberme = new Rememberme();

        private final Authentication authentication = new Authentication();

        @Data
        public static class Authentication {

            private final Jwt jwt = new Jwt();

            @Data
            public static class Jwt {

                private String secret;

                private String reportSecretKey;

                private long tokenValidityInSeconds = 14400;

                private long tokenValidityInSecondsForRememberMe = 2592000;

            }
        }

        @Data
        public static class Rememberme {

            @NotNull
            private String key;

        }
    }

}
