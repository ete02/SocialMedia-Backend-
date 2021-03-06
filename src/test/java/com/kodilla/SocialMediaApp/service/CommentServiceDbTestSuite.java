package com.kodilla.SocialMediaApp.service;

import com.kodilla.SocialMediaApp.domain.entity.Comment;
import com.kodilla.SocialMediaApp.domain.entity.Post;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static com.kodilla.SocialMediaApp.util.EntityDataFixture.*;
import static java.time.temporal.ChronoUnit.SECONDS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@Transactional
@SpringBootTest
public class CommentServiceDbTestSuite {
    @Autowired
    private UserServiceDb userServiceDb;
    @Autowired
    private PostServiceDb postServiceDb;
    @Autowired
    private CommentServiceDb commentServiceDb;

    @BeforeEach
    public void setUp() {
        //GIVEN
        Post post = postServiceDb.savePost(
                createPost(userServiceDb.saveUser(
                        createUser("login", "email@gmail.com")),
                        Instant.now().truncatedTo(SECONDS)));
        commentServiceDb.saveComment(createComment(post));
    }

    @Test
    public void shouldGetAllComments() {
        //WHEN
        List<Comment> comments = commentServiceDb.getAllComments();
        //THEN
        assertEquals(1, comments.size());
    }

    @Test
    public void shouldGetCommentById() {
        //GIVEN
        Comment comment = commentServiceDb.getAllComments().get(0);
        //WHEN
        Comment saveComment = commentServiceDb.getCommentById(comment.getId()).get();
        //THEN
        assertEquals(comment, saveComment);
    }

    @Test
    public void shouldSaveComment() {
        //GIVEN
        Comment comment = createComment(postServiceDb.getAllPosts().get(0));
        //WHEN
        Comment saveComment = commentServiceDb.saveComment(comment);
        //THEN
        assertEquals(2, commentServiceDb.getAllComments().size());
        assertNotEquals(0, saveComment.getId());
    }

    @Test
    public void shouldDeleteCommentById() {
        //GIVEN
        Long commentId = commentServiceDb.getAllComments().get(0).getId();
        //WHEN
        commentServiceDb.deleteCommentById(commentId);
        //THEN
        assertEquals(0, commentServiceDb.getAllComments().size());
        assertEquals(Optional.empty(), commentServiceDb.getCommentById(commentId));
    }

    @Test
    public void shouldDeleteComment() {
        //GIVEN
        Comment comment = commentServiceDb.getAllComments().get(0);
        //WHEN
        commentServiceDb.deleteComment(comment);
        //THEN
        assertEquals(0, commentServiceDb.getAllComments().size());
        assertEquals(Optional.empty(), commentServiceDb.getCommentById(comment.getId()));
    }
}
