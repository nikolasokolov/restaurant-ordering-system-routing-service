package com.graduation.authentication.config;

import com.graduation.authentication.security.AuthoritiesConstants;
import com.graduation.authentication.security.Http401UnauthorizedEntryPoint;
import com.graduation.authentication.security.jwt.JWTConfigurer;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.data.repository.query.SecurityEvaluationContextExtension;

import javax.inject.Inject;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor(onConstructor = @__(@Inject))
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private final Http401UnauthorizedEntryPoint authenticationEntryPoint;
    private final UserDetailsService userDetailsService;
    private final JWTConfigurer jwtConfigurer;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry expressionInterceptUrlRegistry = http
                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint)
                .and()
                .csrf()
                .disable()
                .headers()
                .frameOptions()
                .sameOrigin()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests();

        expressionInterceptUrlRegistry
                .antMatchers(UrlConstants.SUPER_ADMIN_URLS).hasAuthority(AuthoritiesConstants.ROLE_SUPER_ADMIN)
                .antMatchers(UrlConstants.ROLE_ADMIN_URLS).hasAuthority(AuthoritiesConstants.ROLE_ADMIN)
                .antMatchers(UrlConstants.ROLE_USER_URLS).hasAuthority(AuthoritiesConstants.ROLE_USER)
                .antMatchers(UrlConstants.PERMIT_ALL_URLS).permitAll()
                .and()
                .apply(jwtConfigurer);

    }

    @Bean
    public SecurityEvaluationContextExtension securityEvaluationContextExtension() {
        return new SecurityEvaluationContextExtension();
    }
}
