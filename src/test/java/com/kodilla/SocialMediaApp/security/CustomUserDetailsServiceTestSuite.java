package com.kodilla.SocialMediaApp.security;

import com.kodilla.SocialMediaApp.exceptions.custom.user.UserNotFoundException;
import com.kodilla.SocialMediaApp.security.service.CustomUserDetailsService;
import com.kodilla.SocialMediaApp.service.UserServiceDb;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;

import javax.transaction.Transactional;

import static com.kodilla.SocialMediaApp.util.EntityDataFixture.createUser;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Transactional
@SpringBootTest
public class CustomUserDetailsServiceTestSuite {
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    @Autowired
    private UserServiceDb userServiceDb;

    @Test
    public void shouldLoadUserByUsername() {
        //GIVEN
        userServiceDb.saveUser(createUser("login", "test@gmail.com"));
        //WHEN
        UserDetails userDetails = customUserDetailsService.loadUserByUsername("login");
        //THEN
        assertEquals(1, userDetails.getAuthorities().size());
        assertEquals("login", userDetails.getUsername());
        assertEquals("Password", userDetails.getPassword());
    }

    @Test
    public void shouldThrowExceptionDuringLoadUserByUsername() {
        //WHEN
        UserNotFoundException userNotFoundException = assertThrows(
                UserNotFoundException.class,
                () -> customUserDetailsService.loadUserByUsername("login"));
        //THEN
        assertEquals("Could not find user by: login", userNotFoundException.getMessage());
    }
}
