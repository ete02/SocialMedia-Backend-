package com.kodilla.SocialMediaApp.service;

import com.kodilla.SocialMediaApp.domain.dto.CommentRequest;
import com.kodilla.SocialMediaApp.domain.dto.UpdateCommentRequest;
import com.kodilla.SocialMediaApp.domain.entity.Comment;
import com.kodilla.SocialMediaApp.domain.entity.Post;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import java.time.Instant;

import static com.kodilla.SocialMediaApp.util.EntityDataFixture.*;
import static com.kodilla.SocialMediaApp.util.RequestDataFixture.createCommentRequest;
import static com.kodilla.SocialMediaApp.util.RequestDataFixture.createUpdateCommentRequest;
import static java.time.temporal.ChronoUnit.SECONDS;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Transactional
@SpringBootTest
public class CommentServiceTestSuite {
    @Autowired
    private UserServiceDb userServiceDb;
    @Autowired
    private PostServiceDb postServiceDb;
    @Autowired
    private CommentService commentService;
    @Autowired
    private CommentServiceDb commentServiceDb;

    @Test
    public void shouldCreateComment() {
        //GIVEN
        Post post = postServiceDb.savePost(
                createPost(userServiceDb.saveUser(
                        createUser("login", "email@gmail.com")), Instant.now()));
        CommentRequest commentRequest = createCommentRequest();
        //WHEN
        Comment createComment = commentService.createComment(post, commentRequest);
        Instant expectedTime = Instant.now().truncatedTo(SECONDS);
        //THEN
        assertEquals("Test Comment", createComment.getCommentName());
        assertEquals("Test Content", createComment.getContent());
        assertEquals(expectedTime, createComment.getCommentDate().truncatedTo(SECONDS));
        assertEquals(post, createComment.getPost());
        assertEquals(1, commentServiceDb.getAllComments().size());
    }

    @Test
    public void shouldUpdateComment() {
        //GIVEN
        Post post = postServiceDb.savePost(
                createPost(userServiceDb.saveUser(
                        createUser("login", "email@gmail.com")), Instant.now()));
        Comment comment = commentServiceDb.saveComment(createComment(post));
        UpdateCommentRequest updateCommentRequest = createUpdateCommentRequest(comment);
        //WHEN
        Comment updateComment = commentService.updateComment(comment, updateCommentRequest);
        //THEN
        assertEquals(comment, updateComment);
    }
}
