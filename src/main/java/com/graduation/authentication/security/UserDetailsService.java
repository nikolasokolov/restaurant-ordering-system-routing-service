package com.graduation.authentication.security;

import com.graduation.authentication.model.User;
import com.graduation.authentication.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service("userDetailsService")
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {
    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(final String username) {
        log.debug("Authenticating User [{}]", username);
        String lowercaseUsername = username.toLowerCase();
        Optional<User> userFromDatabase = userRepository.findOneByUsername(lowercaseUsername);
        return userFromDatabase
                .map(user -> {
                    List<GrantedAuthority> grantedAuthorities = user.getAuthorities()
                            .stream()
                            .map(authority -> new SimpleGrantedAuthority(authority.getName()))
                            .collect(Collectors.toList());
            return new org.springframework.security.core.userdetails.User(
                    lowercaseUsername, user.getPassword(), grantedAuthorities);
        }).orElseThrow(() -> new UsernameNotFoundException("User " + lowercaseUsername + " was not found in the " +
        "database"));
    }
}
