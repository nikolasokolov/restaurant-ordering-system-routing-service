package com.graduation.authentication.service.impl;

import com.graduation.authentication.config.ApplicationProperties;
import com.graduation.authentication.model.User;
import com.graduation.authentication.security.jwt.JWTConfigurer;
import com.graduation.authentication.security.jwt.TokenProvider;
import com.graduation.authentication.service.AuthenticationService;
import com.graduation.authentication.service.UserService;
import com.graduation.authentication.web.dto.UserData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {

    @Inject
    private UserService userService;

    @Inject
    private TokenProvider tokenProvider;

    @Inject
    private AuthenticationManager authenticationManager;

    @Inject
    private ApplicationProperties applicationProperties;

    @Override
    public ResponseEntity<?> authenticate(String username, String password) {
        try {
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(username, password);
            Authentication authentication = this.authenticationManager.authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = tokenProvider.createToken(authentication, false);
            String jwtToken = "Bearer " + jwt;
            return ResponseEntity.ok(new UserData(username, jwtToken));
        } catch (BadCredentialsException exception) {
            log.warn("Bad credentials for username=" + username);
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
    }

    @Override
    public ResponseEntity logout(HttpServletRequest request, HttpServletResponse response) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currUser = userService.getUser();
        SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();

        if (currUser != null) {
            logoutHandler.logout(request, response, auth);
        }

        Cookie cookie = new Cookie(JWTConfigurer.AUTHORIZATION_HEADER, null);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
        return new ResponseEntity(HttpStatus.ACCEPTED);
    }
}