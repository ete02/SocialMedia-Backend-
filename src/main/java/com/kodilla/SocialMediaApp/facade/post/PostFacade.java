package com.kodilla.SocialMediaApp.facade.post;

import com.kodilla.SocialMediaApp.domain.dto.PostDto;
import com.kodilla.SocialMediaApp.domain.dto.PostRequest;
import com.kodilla.SocialMediaApp.domain.dto.SimplePostRequest;
import com.kodilla.SocialMediaApp.domain.dto.UpdatePostRequest;
import com.kodilla.SocialMediaApp.domain.entity.Post;
import com.kodilla.SocialMediaApp.domain.entity.User;
import com.kodilla.SocialMediaApp.exceptions.custom.post.PostNotFoundException;
import com.kodilla.SocialMediaApp.exceptions.custom.user.UserNotFoundException;
import com.kodilla.SocialMediaApp.mapper.PostMapper;
import com.kodilla.SocialMediaApp.service.ImageService;
import com.kodilla.SocialMediaApp.service.PostServiceDb;
import com.kodilla.SocialMediaApp.service.UserServiceDb;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.kodilla.SocialMediaApp.domain.util.Constants.CREATE_POST_IMAGE_SUCCESS;
import static org.springframework.http.HttpStatus.OK;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class PostFacade {
    private final PostMapper postMapper;
    private final ImageService imageService;
    private final UserServiceDb userServiceDb;
    private final PostServiceDb postServiceDb;
    private final PostFacadeUtils postFacadeUtils;

    public ResponseEntity<List<PostDto>> getPosts() {
        log.info("Get published posts!");
        List<PostDto> posts = postMapper.mapToPostsDto(postServiceDb.getAllPosts());
        return new ResponseEntity<>(posts, OK);
    }

    public ResponseEntity<List<PostDto>> getPostsByLogin(final String login) {
        log.info("Get published posts by login sorted descending!");
        return postFacadeUtils.getPostsIfExists(login);
    }

    public ResponseEntity<PostDto> getPostById(final Long id) {
        log.info("Get published post by id: " + id);
        PostDto postDto = postMapper.mapToPostDto(postServiceDb.getPostById(id)
                .orElseThrow(() -> new PostNotFoundException(id)));
        return new ResponseEntity<>(postDto, OK);
    }

    public ResponseEntity<PostDto> publishPost(final PostRequest postRequest) {
        log.info("Try to publish post!");
        User user = userServiceDb.getUserByLogin(postRequest.getLogin())
                .orElseThrow(() -> new UserNotFoundException(postRequest.getLogin()));
        return postFacadeUtils.createPostIfUserIsAuthorized(postRequest, user);
    }

    public ResponseEntity<String> uploadPostImage(final MultipartFile image, final String postImageName) {
        log.info("Upload post image with name: " + postImageName);
        String result = imageService.loadPostImage(image, postImageName);
        return imageService.getResponseIfImageUploaded(result, CREATE_POST_IMAGE_SUCCESS);
    }

    public ResponseEntity<PostDto> updatePost(final UpdatePostRequest updatePostRequest) {
        log.info("Try to update post!");
        Post post = postServiceDb.getPostById(updatePostRequest.getPostId())
                .orElseThrow(() -> new PostNotFoundException(updatePostRequest.getPostId()));
        return postFacadeUtils.updatePostIfExist(post, updatePostRequest);
    }

    public ResponseEntity<PostDto> likePost(final SimplePostRequest simplePostRequest) {
        log.info("Try to like post!");
        Post post = postServiceDb.getPostById(simplePostRequest.getPostId())
                .orElseThrow(() -> new PostNotFoundException(simplePostRequest.getPostId()));
        User user = userServiceDb.getUserByLogin(simplePostRequest.getLogin())
                .orElseThrow(() -> new UserNotFoundException(simplePostRequest.getLogin()));
        return postFacadeUtils.likePostIfUserIsValidated(simplePostRequest, post, user);
    }

    public ResponseEntity<PostDto> unlikePost(final SimplePostRequest simplePostRequest) {
        log.info("Try to unlike post!");
        Post post = postServiceDb.getPostById(simplePostRequest.getPostId())
                .orElseThrow(() -> new PostNotFoundException(simplePostRequest.getPostId()));
        User user = userServiceDb.getUserByLogin(simplePostRequest.getLogin())
                .orElseThrow(() -> new UserNotFoundException(simplePostRequest.getLogin()));
        return postFacadeUtils.unlikePostIfUserIsValidated(simplePostRequest, post, user);
    }

    public ResponseEntity<String> deletePostById(final Long id) {
        log.info("Delete published post by id: " + id);
        return postFacadeUtils.deletePostIfExists(id);
    }
}
