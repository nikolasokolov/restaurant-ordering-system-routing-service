package com.graduation.authentication.web.dto;

import com.graduation.authentication.model.Authority;
import com.graduation.authentication.model.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.Size;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * A DTO representing a user, with his authorities.
 */
@Data
@NoArgsConstructor
public class UserDTO {

    public static final int PASSWORD_MIN_LENGTH = 4;
    public static final int PASSWORD_MAX_LENGTH = 100;

    @Email
    @Size(min = 5, max = 100)
    private String username;

    @Size(min = PASSWORD_MIN_LENGTH, max = PASSWORD_MAX_LENGTH)
    private String password;

    @Size(max = 150)
    private String name;

    @Size(min = 5, max = 100)
    private String email;

    private boolean activated = false;

    private Set<String> authorities;

    private Long userId;

    public UserDTO(User user) {
        this(user.getUsername(), null, user.getEmail(),
                user.getAuthorities().stream().map(Authority::getName)
                        .collect(Collectors.toSet()), user.getId());
    }

    public UserDTO(String username, String password,
                   String email, Set<String> authorities, Long userId) {

        this.username = username;
        this.password = password;
        this.email = email;
        this.userId = userId;
    }
}
