package com.kodilla.SocialMediaApp.facade;

import com.kodilla.SocialMediaApp.domain.dto.CommentRequest;
import com.kodilla.SocialMediaApp.domain.entity.Post;
import com.kodilla.SocialMediaApp.domain.entity.User;
import com.kodilla.SocialMediaApp.exceptions.custom.comment.CommentNotFoundException;
import com.kodilla.SocialMediaApp.exceptions.custom.user.UserValidationException;
import com.kodilla.SocialMediaApp.facade.comment.CommentFacadeUtils;
import com.kodilla.SocialMediaApp.service.CommentServiceDb;
import com.kodilla.SocialMediaApp.service.PostServiceDb;
import com.kodilla.SocialMediaApp.service.UserServiceDb;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.time.Instant;

import static com.kodilla.SocialMediaApp.util.EntityDataFixture.*;
import static com.kodilla.SocialMediaApp.util.RequestDataFixture.createCommentRequest;
import static java.time.temporal.ChronoUnit.DAYS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Transactional
@SpringBootTest
public class CommentFacadeUtilsTestSuite {
    @Autowired
    CommentFacadeUtils commentFacadeUtils;
    @Autowired
    private PostServiceDb postServiceDb;
    @Autowired
    private UserServiceDb userServiceDb;
    @Autowired
    private CommentServiceDb commentServiceDb;

    @BeforeEach
    public void setUp() {
        //GIVEN
        commentServiceDb.saveComment(createComment(
                postServiceDb.savePost(createPost(
                        userServiceDb.saveUser(createUser("login1", "test1@gmail.com")),
                        Instant.now()))));
        commentServiceDb.saveComment(createComment(
                postServiceDb.savePost(createPost(
                        userServiceDb.saveUser(createUser("login2", "test2@gmail.com")),
                        Instant.now().plus(1, DAYS)))));
        commentServiceDb.saveComment(createComment(
                postServiceDb.savePost(createPost(
                        userServiceDb.saveUser(createUser("login3", "test3@gmail.com")),
                        Instant.now().plus(2, DAYS)))));
    }

    @Test
    public void shouldNotCreateCommentIfUserIsNotAuthorized() {
        //GIVEN
        User user = userServiceDb.getAllUsers().get(0).toBuilder().enabled(false).build();
        Post post = postServiceDb.getAllPosts().get(1);
        CommentRequest commentRequest = createCommentRequest();
        //WHEN
        UserValidationException userValidationException = assertThrows(
                UserValidationException.class,
                () -> commentFacadeUtils.createCommentIfUserIsAuthorized(
                        commentRequest, post, user));
        //THEN
        assertEquals("User Test Comment is not authorized or active!", userValidationException.getMessage());
    }

    @Test
    public void shouldNotDeleteCommentIfNotExistsAndThrow() {
        //GIVEN & WHEN
        CommentNotFoundException commentNotFoundException = assertThrows(CommentNotFoundException.class,
                () -> commentFacadeUtils.deleteCommentIfExists(123L));
        //THEN
        assertEquals("Could not find comment by id: 123", commentNotFoundException.getMessage());
    }
}
