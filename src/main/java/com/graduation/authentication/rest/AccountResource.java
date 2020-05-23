package com.graduation.authentication.rest;

import com.graduation.authentication.rest.dto.UserDetailsDTO;
import com.graduation.authentication.domain.User;
import com.graduation.authentication.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static java.util.Objects.nonNull;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api")
public class AccountResource {

    private final UserService userService;

    @RequestMapping(value = "/account", method = RequestMethod.GET)
    public ResponseEntity<UserDetailsDTO> getUserDetails() {
        User user = userService.getUser();
        return nonNull(user) ? ResponseEntity.ok(new UserDetailsDTO(user)) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
