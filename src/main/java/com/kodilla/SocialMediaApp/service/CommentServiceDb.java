package com.kodilla.SocialMediaApp.service;

import com.kodilla.SocialMediaApp.domain.entity.Comment;
import com.kodilla.SocialMediaApp.repository.CommentRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CommentServiceDb {
    private final CommentRepo commentDbRepository;

    public List<Comment> getAllComments() {
        return commentDbRepository.findAll();
    }

    public Optional<Comment> getCommentById(final Long id) {
        return commentDbRepository.findById(id);
    }

    public Comment saveComment(final Comment comment) {
        return commentDbRepository.save(comment);
    }

    public void deleteCommentById(final Long id) {
        commentDbRepository.deleteById(id);
    }

    public void deleteComment(final Comment comment) {
        commentDbRepository.delete(comment);
    }
}
