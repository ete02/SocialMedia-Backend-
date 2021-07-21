package com.kodilla.SocialMediaApp.service;

import com.kodilla.SocialMediaApp.domain.entity.Role;
import com.kodilla.SocialMediaApp.domain.enums.RoleType;
import com.kodilla.SocialMediaApp.repository.RoleRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class RoleServiceDb {
    private final RoleRepo roleRepository;

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    public List<Role> getRolesByRoleType(final RoleType roleType) {
        return roleRepository.findAllByRoleType(roleType);
    }

    public List<Role> getRolesByUserLogin(final String login) {
        return roleRepository.findAllByLogin(login);
    }

    public Optional<Role> getRoleById(final Long id) {
        return roleRepository.findById(id);
    }

    public Role saveRole(final Role role) {
        return roleRepository.save(role);
    }

    public void deleteRoleById(final Long id) {
        roleRepository.deleteById(id);
    }

    public void deleteRole(final Role role) {
        roleRepository.delete(role);
    }
}
