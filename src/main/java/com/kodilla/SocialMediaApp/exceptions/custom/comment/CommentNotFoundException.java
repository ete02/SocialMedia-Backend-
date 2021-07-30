package com.kodilla.SocialMediaApp.exceptions.custom.comment;

public class CommentNotFoundException extends RuntimeException {
    public CommentNotFoundException(Long id) {
        super("Could not find comment by id: " + id);
    }
}
