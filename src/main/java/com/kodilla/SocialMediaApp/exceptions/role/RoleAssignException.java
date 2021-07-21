package com.kodilla.SocialMediaApp.exceptions.role;

import com.kodilla.SocialMediaApp.domain.enums.RoleType;

public class RoleAssignException extends RuntimeException{
    public RoleAssignException(RoleType roleType) {
        super("User is already assigned to role: " + roleType);
    }
}
