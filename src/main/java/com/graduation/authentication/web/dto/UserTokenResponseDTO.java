package com.graduation.authentication.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserTokenResponseDTO {
    private String username;
    private String token;
    private Long expiresIn;
}
