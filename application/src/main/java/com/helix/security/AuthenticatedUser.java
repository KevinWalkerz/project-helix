package com.helix.security;

import com.helix.zibrary.data.security.entities.UserCredentials;
import com.helix.zibrary.data.security.repositories.UserCredentialsRepository;
import com.vaadin.flow.spring.security.AuthenticationContext;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuthenticatedUser {

    private final AuthenticationContext authenticationContext;
    private final UserCredentialsRepository userCredentialsRepository;

    public AuthenticatedUser(AuthenticationContext authenticationContext,
                             UserCredentialsRepository userCredentialsRepository) {
        this.authenticationContext = authenticationContext;
        this.userCredentialsRepository = userCredentialsRepository;
    }

    public Optional<UserCredentials> get() {
        return authenticationContext.getAuthenticatedUser(UserDetails.class)
                .flatMap(userDetails -> userCredentialsRepository.findUserByUsername(userDetails.getUsername()));
    }

    public void logout() {
        authenticationContext.logout();
    }
}
