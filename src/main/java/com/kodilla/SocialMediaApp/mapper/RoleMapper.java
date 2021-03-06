package com.kodilla.SocialMediaApp.mapper;

import com.kodilla.SocialMediaApp.domain.dto.RoleDto;
import com.kodilla.SocialMediaApp.domain.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "users", ignore = true)
    Role mapToRole(RoleDto roleDto);

    RoleDto mapToRoleDto(Role role);

    List<RoleDto> mapToRolesDto(List<Role> roles);
}
