package com.graduation.authentication.security.jwt;

import com.graduation.authentication.security.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

@Component
public class JWTConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    @Autowired
    private SecurityUtils securityUtils;

    public final static String AUTHORIZATION_HEADER = "Authorization";

    public final static String AUTHORIZATION_TOKEN = "access_token";

    @Override
    public void configure(HttpSecurity http) {
        JWTFilter customFilter = new JWTFilter(securityUtils);
        http.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
