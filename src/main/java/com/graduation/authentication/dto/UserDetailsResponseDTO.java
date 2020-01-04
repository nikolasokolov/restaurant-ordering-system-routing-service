package com.graduation.authentication.dto;

import com.graduation.authentication.model.Authority;
import com.graduation.authentication.model.Company;
import com.graduation.authentication.model.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class UserDetailsResponseDTO {
    private Long id;
    private String username;
    private String password;
    private String email;
    private Set<String> authorities;
    private Company company;

    public UserDetailsResponseDTO(User user) {
        this(user.getId(), user.getUsername(), null, user.getEmail(),
                user.getAuthorities().stream().map(Authority::getName)
                        .collect(Collectors.toSet()), user.getCompany());
    }

    public UserDetailsResponseDTO(Long id, String username, String password,
                                  String email, Set<String> authorities, Company company) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.authorities = authorities;
        this.company = company;
    }
}
