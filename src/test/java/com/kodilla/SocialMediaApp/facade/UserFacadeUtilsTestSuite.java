package com.kodilla.SocialMediaApp.facade;

import com.kodilla.SocialMediaApp.domain.entity.Role;
import com.kodilla.SocialMediaApp.domain.entity.User;
import com.kodilla.SocialMediaApp.domain.entity.VerificationToken;
import com.kodilla.SocialMediaApp.facade.user.UserFacadeUtils;
import com.kodilla.SocialMediaApp.service.RoleServiceDb;
import com.kodilla.SocialMediaApp.service.UserServiceDb;
import com.kodilla.SocialMediaApp.service.VerificationTokenServiceDb;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import javax.transaction.Transactional;
import java.util.List;

import static com.kodilla.SocialMediaApp.domain.enums.RoleType.USER;
import static com.kodilla.SocialMediaApp.util.EntityDataFixture.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.HttpStatus.OK;

@Transactional
@SpringBootTest
public class UserFacadeUtilsTestSuite {
    @Autowired
    private UserFacadeUtils userFacadeUtils;
    @Autowired
    private UserServiceDb userServiceDb;
    @Autowired
    private RoleServiceDb roleServiceDb;
    @Autowired
    private VerificationTokenServiceDb verificationTokenServiceDb;

    @BeforeEach
    public void setUp() {
        //GIVEN
        User user = userServiceDb.saveUser(createUser("login_1", "email1@gmail.com"));
        verificationTokenServiceDb.saveVerificationToken(createVerificationToken(user, "123"));
        roleServiceDb.saveRole(createRole(USER, user));
        userServiceDb.saveUser(createUser("next_login_2", "email2@gmail.com"));
        userServiceDb.saveUser(createUser("logging_3", "email3@gmail.com"));
    }

    @Test
    public void shouldDeleteValidatedUser() {
        //GIVEN
        User user = userServiceDb.getUserByLogin("login_1").get();
        List<VerificationToken> verificationToken = verificationTokenServiceDb.getUserValidVerificationToken(user);
        List<Role> roles = roleServiceDb.getRolesByUserLogin("login_1");
        //WHEN
        ResponseEntity<String> responseEntity = userFacadeUtils.deleteValidatedUser(user, verificationToken, roles);
        //THEN
        assertEquals(OK, responseEntity.getStatusCode());
        assertEquals("User Deleted Successfully!", responseEntity.getBody());
    }
}
