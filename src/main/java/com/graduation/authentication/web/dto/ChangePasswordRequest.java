package com.graduation.authentication.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChangePasswordRequest {
    private String username;
    private String currentPassword;
    private String newPassword;
    private String confirmPassword;
}
