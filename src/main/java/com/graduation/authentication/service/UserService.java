package com.graduation.authentication.service;

import com.graduation.authentication.model.User;
import com.graduation.authentication.web.dto.ChangePasswordRequest;

public interface UserService {
    User getUser();
    boolean changePassword(ChangePasswordRequest changePasswordRequest) throws Exception;
    User save(User user);
}
