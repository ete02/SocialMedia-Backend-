package com.kodilla.SocialMediaApp.service;

import com.kodilla.SocialMediaApp.domain.entity.Comment;
import com.kodilla.SocialMediaApp.repository.CommentRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CommentService {
    private final CommentRepo commentRepository;

    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    public Optional<Comment> getCommentById(final Long id) {
        return commentRepository.findById(id);
    }

    public Comment saveComment(final Comment comment) {
        return commentRepository.save(comment);
    }

    public void deleteCommentById(final Long id) {
        commentRepository.deleteById(id);
    }
}
