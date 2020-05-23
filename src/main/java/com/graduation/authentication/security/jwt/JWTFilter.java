package com.graduation.authentication.security.jwt;

import com.graduation.authentication.security.SecurityUtils;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JWTFilter extends GenericFilterBean {

    private static final String OPTIONS = "OPTIONS";

    private final SecurityUtils securityUtils;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        try {
            HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
            HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
            String jwt = SecurityUtils.resolveToken(httpServletRequest);

            // Skip checking token when user is trying to authenticate, because token will not be present
            if (((HttpServletRequest) servletRequest).getRequestURI().contains("/main/") ||
                    ((HttpServletRequest) servletRequest).getRequestURI().contains("/account/")) {
                if (StringUtils.hasText(jwt)) {
                    if (securityUtils.isValidToken(jwt)) {
                        Authentication authentication = securityUtils.getAuthenticationFromToken(jwt);
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                        SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
                    } else {
                        unAuthorizeResponse(servletResponse);
                    }
                } else {
                    if (!httpServletRequest.getMethod().equals(OPTIONS)) {
                        unAuthorizeResponse(servletResponse);
                    }
                }
            }
            filterChain.doFilter(servletRequest, servletResponse);
        } catch (ExpiredJwtException eje) {
            log.info("Security exception for user {} - {}", eje.getClaims().getSubject(), eje.getMessage());
            unAuthorizeResponse(servletResponse, "{\"error\":\"JWT expired\"}");
        }
    }

    private void unAuthorizeResponse(ServletResponse servletResponse) {
        unAuthorizeResponse(servletResponse, null);
    }

    private void unAuthorizeResponse(ServletResponse servletResponse, String responseBody) {
        HttpServletResponse unAuthorizedResponse = ((HttpServletResponse) servletResponse);
        unAuthorizedResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        if (responseBody != null) {
            try {
                unAuthorizedResponse.getWriter().write(responseBody);
            } catch (IOException e) {
                log.error("Unable to write a message to the response body on token expired.");
            }
        }
    }
}
