package com.graduation.authentication.service;

import com.graduation.authentication.rest.dto.UserTokenDTO;

import java.util.Optional;

public interface AuthenticationService {

    Optional<UserTokenDTO> authenticate(String username, String password);
}
