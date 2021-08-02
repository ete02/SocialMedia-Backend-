package com.kodilla.SocialMediaApp.facade.post;

import com.kodilla.SocialMediaApp.domain.dto.PostDto;
import com.kodilla.SocialMediaApp.domain.dto.PostRequest;
import com.kodilla.SocialMediaApp.domain.dto.SimplePostRequest;
import com.kodilla.SocialMediaApp.domain.dto.UpdatePostRequest;
import com.kodilla.SocialMediaApp.domain.entity.Post;
import com.kodilla.SocialMediaApp.domain.entity.User;
import com.kodilla.SocialMediaApp.exceptions.custom.post.PostNotFoundByLoginException;
import com.kodilla.SocialMediaApp.exceptions.custom.post.PostNotFoundException;
import com.kodilla.SocialMediaApp.exceptions.custom.user.UserValidationException;
import com.kodilla.SocialMediaApp.mapper.PostMapper;
import com.kodilla.SocialMediaApp.service.PostService;
import com.kodilla.SocialMediaApp.service.PostServiceDb;
import com.kodilla.SocialMediaApp.service.UserServiceDb;
import com.kodilla.SocialMediaApp.validator.UserValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.kodilla.SocialMediaApp.domain.enums.ValidationStatus.*;
import static org.springframework.http.HttpStatus.OK;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Component
public class PostFacadeUtils {
    private final PostMapper postMapper;
    private final PostService postService;
    private final PostServiceDb postServiceDb;
    private final UserServiceDb userServiceDb;
    private final UserValidator userValidator;

    public ResponseEntity<List<PostDto>> getPostsIfExists(final String login) {
        try {
            List<PostDto> posts = postMapper.mapToPostsDto(postServiceDb.getAllPostsByLoginSortedDescending(login));
            log.info("Published posts returned successfully!");
            return new ResponseEntity<>(posts, OK);
        } catch (Exception e) {
            throw new PostNotFoundByLoginException(login);
        }
    }

    public ResponseEntity<PostDto> createPostIfUserIsAuthorized(final PostRequest postRequest, final User user) {
        if (userValidator.isUserValidated(user)) {
            PostDto postDto = postMapper.mapToPostDto(postService.createPost(user, postRequest));
            log.info("Published post returned successfully!");
            return new ResponseEntity<>(postDto, OK);
        } else {
            throw new UserValidationException(postRequest.getLogin(), AUTHORIZED);
        }
    }

    public ResponseEntity<PostDto> updatePostIfExist(final Post post, final UpdatePostRequest updatePostRequest) {
        PostDto postDto = postMapper.mapToPostDto(postService.updatePost(post, updatePostRequest));
        log.info("Post updated successfully!");
        return new ResponseEntity<>(postDto, OK);
    }

    public ResponseEntity<PostDto> likePostIfUserIsValidated(final SimplePostRequest simplePostRequest, final Post post, final User user) {
        if (userValidator.isUserValidatedToLikePost(user, post)) {
            post.countUp();
            user.getLikedPosts().add(post);
            userServiceDb.saveUser(user);
            log.info("Post liked successfully!");
            return new ResponseEntity<>(postMapper.mapToPostDto(post), OK);
        } else {
            throw new UserValidationException(simplePostRequest.getLogin(), AUTHORIZED_CONTAINS_POST_LIKED);
        }
    }

    public ResponseEntity<PostDto> unlikePostIfUserIsValidated(final SimplePostRequest simplePostRequest, final Post post, final User user) {
        if (userValidator.isUserValidatedToUnlikePost(user, post)) {
            post.countDown();
            user.getLikedPosts().remove(post);
            userServiceDb.saveUser(user);
            log.info("Post unliked successfully!");
            return new ResponseEntity<>(postMapper.mapToPostDto(post), OK);
        } else {
            throw new UserValidationException(simplePostRequest.getLogin(), AUTHORIZED_CONTAINS_POST_UNLIKED);
        }
    }

    public ResponseEntity<String> deletePostIfExists(final Long id) {
        try {
            postServiceDb.deletePostById(id);
            log.info("Post deleted successfully!");
        } catch (EmptyResultDataAccessException e) {
            throw new PostNotFoundException(id);
        }
        return new ResponseEntity<>("Post Deleted Successfully!", OK);
    }
}
