package com.kodilla.SocialMediaApp.facade.role;

import com.kodilla.SocialMediaApp.domain.dto.RoleDto;
import com.kodilla.SocialMediaApp.domain.dto.RoleRequest;
import com.kodilla.SocialMediaApp.domain.entity.User;
import com.kodilla.SocialMediaApp.exceptions.custom.role.RoleNotFoundException;
import com.kodilla.SocialMediaApp.exceptions.custom.user.UserNotFoundException;
import com.kodilla.SocialMediaApp.mapper.RoleMapper;
import com.kodilla.SocialMediaApp.service.RoleServiceDb;
import com.kodilla.SocialMediaApp.service.UserServiceDb;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class RoleFacade {
    private final RoleMapper roleMapper;
    private final RoleServiceDb roleServiceDb;
    private final UserServiceDb userServiceDb;
    private final RoleFacadeUtils roleFacadeUtils;

    public ResponseEntity<List<RoleDto>> getRoles() {
        log.info("Get available roles!");
        List<RoleDto> roles = roleMapper.mapToRolesDto(roleServiceDb.getAllRoles());
        return new ResponseEntity<>(roles, OK);
    }

    public ResponseEntity<List<RoleDto>> getRolesByLogin(final String login) {
        log.info("Get available role by login: " + login);
        User user = userServiceDb.getUserByLogin(login)
                .orElseThrow(() -> new UserNotFoundException(login));
        List<RoleDto> roles = roleMapper.mapToRolesDto(roleServiceDb.getRolesByUserLogin(user.getLogin()));
        return new ResponseEntity<>(roles, OK);
    }

    public ResponseEntity<RoleDto> getRoleById(final Long id) {
        log.info("Get available role by id: " + id);
        RoleDto roleDto = roleMapper.mapToRoleDto(roleServiceDb.getRoleById(id)
                .orElseThrow(() -> new RoleNotFoundException(id)));
        return new ResponseEntity<>(roleDto, OK);
    }

    public ResponseEntity<RoleDto> assignRoleToUser(final RoleRequest roleRequest) {
        log.info("Try assign user: " + roleRequest.getLogin() + " to role: " + roleRequest.getRoleType());
        User userFromDb = userServiceDb.getUserByLogin(roleRequest.getLogin())
                .orElseThrow(() -> new UserNotFoundException(roleRequest.getLogin()));
        return roleFacadeUtils.assignRoleIfUserIsValidated(roleRequest, userFromDb);
    }

    public ResponseEntity<String> deleteRoleById(final Long id) {
        log.info("Delete available role by id: " + id);
        return roleFacadeUtils.deleteRoleIfExists(id);
    }
}
