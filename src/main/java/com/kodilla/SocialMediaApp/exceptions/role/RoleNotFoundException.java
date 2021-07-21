package com.kodilla.SocialMediaApp.exceptions.role;

public class RoleNotFoundException extends RuntimeException{
    public RoleNotFoundException(Long id) {
        super("Could not find role by id: " + id);
    }
}
