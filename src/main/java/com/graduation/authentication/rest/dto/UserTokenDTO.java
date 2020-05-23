package com.graduation.authentication.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserTokenDTO {

    private String username;
    private String token;
    private Long expiresIn;
}
