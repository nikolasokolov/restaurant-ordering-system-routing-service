package com.graduation.authentication.security.jwt;

import com.graduation.authentication.config.ApplicationProperties;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.Date;
import java.util.stream.Collectors;

@Component
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class TokenProvider {
    private static final String AUTHORITIES_KEY = "auth";
    private final ApplicationProperties applicationProperties;

    private String secretKey;
    private long tokenValidityInSeconds;

    @PostConstruct
    public void init() {
        this.secretKey =  applicationProperties.getSecurity().getAuthentication().getJwt().getSecret();
        this.tokenValidityInSeconds = 1000 * applicationProperties.getSecurity()
                .getAuthentication().getJwt().getTokenValidityInSeconds();
    }

    public String createToken(Authentication authentication) {
        String authorities = authentication.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.joining(","));

        long now = (new Date()).getTime();
        Date validity = new Date(now + this.tokenValidityInSeconds);

        return Jwts.builder()
            .setSubject(authentication.getName())
            .claim(AUTHORITIES_KEY, authorities)
            .signWith(SignatureAlgorithm.HS512, secretKey)
            .setExpiration(validity)
            .compact();
    }
}
