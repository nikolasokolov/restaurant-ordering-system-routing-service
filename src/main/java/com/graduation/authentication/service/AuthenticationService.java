package com.graduation.authentication.service;

import com.graduation.authentication.dto.UserTokenResponseDTO;

public interface AuthenticationService {
    UserTokenResponseDTO authenticate(String username, String password);
}
