package com.kodilla.SocialMediaApp.controller;

import com.kodilla.SocialMediaApp.domain.dto.RoleDto;
import com.kodilla.SocialMediaApp.domain.dto.RoleRequest;
import com.kodilla.SocialMediaApp.facade.role.RoleFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/roles")
public class RoleController {
    private final RoleFacade roleFacade;

    @GetMapping
    public ResponseEntity<List<RoleDto>> getRoles() {
        return roleFacade.getRoles();
    }

    @GetMapping("/{login}")
    public ResponseEntity<List<RoleDto>> getRolesByLogin(@PathVariable final String login) {
        return roleFacade.getRolesByLogin(login);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<RoleDto> getRoleById(@PathVariable final Long id) {
        return roleFacade.getRoleById(id);
    }

    @PostMapping("/assign")
    public ResponseEntity<RoleDto> assignRoleToUser(@RequestBody final RoleRequest roleRequest) {
        return roleFacade.assignRoleToUser(roleRequest);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRoleById(@PathVariable final Long id) {
        return roleFacade.deleteRoleById(id);
    }
}
