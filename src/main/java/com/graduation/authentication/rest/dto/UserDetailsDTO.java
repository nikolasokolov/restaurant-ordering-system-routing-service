package com.graduation.authentication.rest.dto;

import com.graduation.authentication.model.Authority;
import com.graduation.authentication.model.Company;
import com.graduation.authentication.model.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class UserDetailsDTO {

    private Long id;
    private String username;
    private String password;
    private String email;
    private Set<String> authorities;
    private Company company;

    public UserDetailsDTO(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.email = user.getEmail();
        this.authorities = user.getAuthorities().stream().map(Authority::getName).collect(Collectors.toSet());
        this.company = user.getCompany();
    }
}
