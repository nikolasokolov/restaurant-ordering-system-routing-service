package com.graduation.authentication.rest;

import com.graduation.authentication.service.AuthenticationService;
import com.graduation.authentication.dto.LoginRequestDTO;
import com.graduation.authentication.dto.UserTokenResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.Objects;

@Slf4j
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> authenticate(@RequestBody LoginRequestDTO loginRequestDTO) {
        UserTokenResponseDTO userTokenResponseDTO = authenticationService.authenticate(loginRequestDTO.getUsername(), loginRequestDTO.getPassword());
        if (Objects.nonNull(userTokenResponseDTO)) {
            return new ResponseEntity<>(userTokenResponseDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
}
