package com.graduation.authentication.service.impl;

import com.graduation.authentication.model.User;
import com.graduation.authentication.repository.UserRepository;
import com.graduation.authentication.security.SecurityUtils;
import com.graduation.authentication.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class UserServiceImpl implements UserService {
    private final SecurityUtils securityUtils;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public User getUser() {
        String authenticatedUser = securityUtils.getAuthenticatedUsername();
        return userRepository.findOneByUsername(authenticatedUser).orElse(null);
    }
}
