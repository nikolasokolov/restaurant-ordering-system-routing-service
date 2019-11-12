package com.graduation.authentication.service;

import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface AuthenticationService {
    ResponseEntity authenticate(String username, String password);
    ResponseEntity logout(HttpServletRequest request, HttpServletResponse response);
}
