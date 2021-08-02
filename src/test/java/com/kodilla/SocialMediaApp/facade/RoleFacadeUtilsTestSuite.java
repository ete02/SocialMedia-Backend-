package com.kodilla.SocialMediaApp.facade;

import com.kodilla.SocialMediaApp.domain.dto.RoleRequest;
import com.kodilla.SocialMediaApp.domain.entity.User;
import com.kodilla.SocialMediaApp.exceptions.custom.role.RoleAssignException;
import com.kodilla.SocialMediaApp.exceptions.custom.role.RoleNotFoundException;
import com.kodilla.SocialMediaApp.facade.role.RoleFacadeUtils;
import com.kodilla.SocialMediaApp.service.RoleServiceDb;
import com.kodilla.SocialMediaApp.service.UserServiceDb;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import static com.kodilla.SocialMediaApp.domain.enums.RoleType.*;
import static com.kodilla.SocialMediaApp.util.EntityDataFixture.createRole;
import static com.kodilla.SocialMediaApp.util.EntityDataFixture.createUser;
import static com.kodilla.SocialMediaApp.util.RequestDataFixture.createRoleRequest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Transactional
@SpringBootTest
public class RoleFacadeUtilsTestSuite {
    @Autowired
    private RoleFacadeUtils roleFacadeUtils;
    @Autowired
    private RoleServiceDb roleServiceDb;
    @Autowired
    private UserServiceDb userServiceDb;

    @BeforeEach
    public void setUp() {
        //GIVEN
        User user = userServiceDb.saveUser(createUser("login_1", "email1@gmail.com"));
        User anotherUser = userServiceDb.saveUser(createUser("login_2", "email2@gmail.com"));
        roleServiceDb.saveRole(createRole(MODERATOR, user));
        roleServiceDb.saveRole(createRole(ADMIN, user, anotherUser));
        roleServiceDb.saveRole(createRole(USER, anotherUser));
        roleServiceDb.saveRole(createRole(MODERATOR, anotherUser));
    }

    @Test
    public void shouldNotAssignRoleIfUserIsValidatedAndUserNotFoundException() {
        //GIVEN
        User user = userServiceDb.getAllUsers().get(0);
        RoleRequest roleRequest = createRoleRequest(MODERATOR);
        //WHEN
        RoleAssignException roleAssignException = assertThrows(RoleAssignException.class,
                () -> roleFacadeUtils.assignRoleIfUserIsValidated(roleRequest, user));
        //THEN
        assertEquals("User is already assigned to role: MODERATOR", roleAssignException.getMessage());
    }

    @Test
    public void shouldNotDeleteRoleIfExistsAndRoleNotFoundException() {
        //WHEN
        RoleNotFoundException roleNotFoundException = assertThrows(
                RoleNotFoundException.class,
                () -> roleFacadeUtils.deleteRoleIfExists(123L));
        //THEN
        assertEquals("Could not find role by id: 123", roleNotFoundException.getMessage());
    }
}
