package com.graduation.authentication.web.resource;

import com.graduation.authentication.service.AuthenticationService;
import com.graduation.authentication.web.dto.LoginRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class UserJWTController {
    private final AuthenticationService authenticationService;

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> authorize(@RequestBody LoginRequest loginRequest, HttpServletResponse httpServletResponse) {
        return authenticationService.authenticate(loginRequest.getUsername(), loginRequest.getPassword());
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public ResponseEntity logout(HttpServletRequest request, HttpServletResponse response) {
        return authenticationService.logout(request, response);
    }

    @RequestMapping(value = "/api/test", method = RequestMethod.GET)
    public ResponseEntity test() {
        log.info("Test request received");
        return ResponseEntity.ok("OK");
    }
}
