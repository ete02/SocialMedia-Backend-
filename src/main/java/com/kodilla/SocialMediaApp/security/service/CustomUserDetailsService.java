package com.kodilla.SocialMediaApp.security.service;

import com.kodilla.SocialMediaApp.domain.entity.User;
import com.kodilla.SocialMediaApp.exceptions.custom.user.UserNotFoundException;
import com.kodilla.SocialMediaApp.service.UserServiceDb;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Collection;

import static java.util.Collections.singletonList;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserServiceDb userServiceDb;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(final String login) {
        User user = userServiceDb.getUserByLogin(login).orElseThrow(() ->
                new UserNotFoundException(login));
        return createUserDetailsUser(user);
    }

    private org.springframework.security.core.userdetails.User createUserDetailsUser(final User user) {
        return new org.springframework.security.core.userdetails.User(
                user.getLogin(), user.getPassword(), user.isEnabled(),
                true, true, true, getAuthorities("USER"));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(final String role) {
        return singletonList(new SimpleGrantedAuthority(role));
    }
}
