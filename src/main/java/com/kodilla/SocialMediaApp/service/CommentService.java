package com.kodilla.SocialMediaApp.service;

import com.kodilla.SocialMediaApp.domain.dto.CommentRequest;
import com.kodilla.SocialMediaApp.domain.dto.UpdateCommentRequest;
import com.kodilla.SocialMediaApp.domain.entity.Comment;
import com.kodilla.SocialMediaApp.domain.entity.Post;
import com.kodilla.SocialMediaApp.repository.CommentRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CommentService {
    private final CommentServiceDb commentServiceDb;

    public Comment createComment(final Post post, final CommentRequest commentRequest) {
        return commentServiceDb.saveComment(Comment.builder()
                .commentName(commentRequest.getLogin())
                .content(commentRequest.getContent())
                .commentDate(Instant.now())
                .post(post)
                .build());
    }

    public Comment updateComment(final Comment comment, final UpdateCommentRequest updateCommentRequest) {
        return commentServiceDb.saveComment(comment.toBuilder()
                .commentName(updateCommentRequest.getCommentName())
                .content(updateCommentRequest.getContent())
                .updateDate(Instant.now())
                .build());
    }
}
