package com.graduation.authentication.rest;

import com.graduation.authentication.dto.UserDetailsResponseDTO;
import com.graduation.authentication.model.User;
import com.graduation.authentication.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.Objects;

@Slf4j
@RestController
@RequestMapping(value = "/api")
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class AccountResource {
    private final UserService userService;

    @RequestMapping(value = "/account", method = RequestMethod.GET)
    public ResponseEntity<UserDetailsResponseDTO> getAccount() {
        User user = userService.getUser();
        if (Objects.nonNull(user)) {
            return new ResponseEntity<>(new UserDetailsResponseDTO(user), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
