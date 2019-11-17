package com.graduation.authentication.web.resource;

import com.graduation.authentication.model.User;
import com.graduation.authentication.service.UserService;
import com.graduation.authentication.web.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@RestController
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class AccountResource {
    private final UserService userService;

    @RequestMapping(value = "/authenticate", method = RequestMethod.GET)
    public String isAuthenticated(HttpServletRequest request) {
        log.debug("REST request to check if the current user is authenticated");
        return request.getRemoteUser();
    }

    @RequestMapping(value = "/account", method = RequestMethod.GET)
    public ResponseEntity<UserDTO> getAccount() {
        final Optional<User> optionalUser = Optional.ofNullable(userService.getUser());
        return optionalUser.map(user -> new ResponseEntity<>(new UserDTO(user), HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

}
