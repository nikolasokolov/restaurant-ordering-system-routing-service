package com.graduation.authentication.service.impl;

import com.graduation.authentication.model.User;
import com.graduation.authentication.repository.UserRepository;
import com.graduation.authentication.security.SecurityUtils;
import com.graduation.authentication.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final SecurityUtils securityUtils;
    private final UserRepository userRepository;

    @Override
    public User getUser() {
        String authenticatedUser = securityUtils.getAuthenticatedUsername();
        return userRepository.findOneByUsername(authenticatedUser).orElse(null);
    }
}
