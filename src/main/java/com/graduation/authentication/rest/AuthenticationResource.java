package com.graduation.authentication.rest;

import com.graduation.authentication.service.AuthenticationService;
import com.graduation.authentication.rest.dto.UserCredentialsDTO;
import com.graduation.authentication.rest.dto.UserTokenDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthenticationResource {

    private final AuthenticationService authenticationService;

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<UserTokenDTO> authenticate(@RequestBody UserCredentialsDTO userCredentialsDTO) {
        log.info("Started authenticating User with username=[{}]", userCredentialsDTO.getUsername());
        Optional<UserTokenDTO> userTokenDTO = authenticationService.authenticate(userCredentialsDTO.getUsername(), userCredentialsDTO.getPassword());
        if (userTokenDTO.isPresent()) {
            log.info("Finished authenticating User with username=[{}]", userCredentialsDTO.getUsername());
            return ResponseEntity.ok(userTokenDTO.get());
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
}
