package com.graduation.authentication.service.impl;

import com.graduation.authentication.config.ApplicationProperties;
import com.graduation.authentication.security.jwt.TokenProvider;
import com.graduation.authentication.service.AuthenticationService;
import com.graduation.authentication.rest.dto.UserTokenDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final TokenProvider tokenProvider;
    private final AuthenticationManager authenticationManager;
    private final ApplicationProperties applicationProperties;

    @Override
    public Optional<UserTokenDTO> authenticate(String username, String password) {
        try {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = tokenProvider.createToken(authentication);
            String jwtToken = "Bearer " + jwt;
            Long expiresIn = applicationProperties.getSecurity().getAuthentication().getJwt().getTokenValidityInSeconds();
            return Optional.of(new UserTokenDTO(username, jwtToken, expiresIn));
        } catch (BadCredentialsException exception) {
            log.warn("Failed to authenticate User with username=[{}]", username);
            return Optional.empty();
        }
    }
}
