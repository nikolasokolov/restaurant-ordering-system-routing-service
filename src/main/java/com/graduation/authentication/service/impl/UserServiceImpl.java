package com.graduation.authentication.service.impl;

import com.graduation.authentication.model.User;
import com.graduation.authentication.repository.UserRepository;
import com.graduation.authentication.security.SecurityUtils;
import com.graduation.authentication.service.UserService;
import com.graduation.authentication.web.dto.ChangePasswordRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class UserServiceImpl implements UserService {
    private final SecurityUtils securityUtils;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public User getUser() {
        return userRepository.findOneByUsername(securityUtils.getAuthenticatedUsername()).orElse(null);
    }

    @Override
    @Transactional
    public boolean changePassword(ChangePasswordRequest changePasswordRequest) throws Exception {
        if (!changePasswordRequest.getNewPassword().equals(changePasswordRequest.getConfirmPassword())) {
            throw new Exception("Passwords doesn't match");
        }
        Optional<User> userOptional = userRepository.findOneByUsername(changePasswordRequest.getUsername());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            String currentPasswordFromRequest = changePasswordRequest.getCurrentPassword();
            String newPasswordEncrypted = passwordEncoder.encode(changePasswordRequest.getNewPassword());
            boolean currentPasswordMatch = passwordEncoder.matches(currentPasswordFromRequest, user.getPassword());
            if (currentPasswordMatch) {
                user.setPassword(newPasswordEncrypted);
                this.save(user);
                return true;
            } else {
                throw new Exception("Current password is not correct");
            }
        }
        return false;
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }
}
