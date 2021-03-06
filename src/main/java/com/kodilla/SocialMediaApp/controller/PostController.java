package com.kodilla.SocialMediaApp.controller;

import com.kodilla.SocialMediaApp.domain.dto.PostDto;
import com.kodilla.SocialMediaApp.domain.dto.PostRequest;
import com.kodilla.SocialMediaApp.domain.dto.SimplePostRequest;
import com.kodilla.SocialMediaApp.domain.dto.UpdatePostRequest;
import com.kodilla.SocialMediaApp.facade.post.PostFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {
    private final PostFacade postFacade;

    @GetMapping
    public ResponseEntity<List<PostDto>> getPosts() {
        return postFacade.getPosts();
    }

    @GetMapping("/{login}")
    public ResponseEntity<List<PostDto>> getPostsByLogin(@PathVariable final String login) {
        return postFacade.getPostsByLogin(login);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable final Long id) {
        return postFacade.getPostById(id);
    }

    @PostMapping
    public ResponseEntity<PostDto> publishPost(@RequestBody final PostRequest postRequest) {
        return postFacade.publishPost(postRequest);
    }

    @PostMapping("/upload/{postImageName}")
    public ResponseEntity<String> uploadPostImage(@RequestParam("image") final MultipartFile image, @PathVariable final String postImageName) {
        return postFacade.uploadPostImage(image, postImageName);
    }

    @PutMapping
    public ResponseEntity<PostDto> updatePost(@RequestBody final UpdatePostRequest updatePostRequest) {
        return postFacade.updatePost(updatePostRequest);
    }

    @PutMapping("/like")
    public ResponseEntity<PostDto> likePost(@RequestBody final SimplePostRequest simplePostRequest) {
        return postFacade.likePost(simplePostRequest);
    }

    @PutMapping("/unlike")
    public ResponseEntity<PostDto> unlikePost(@RequestBody final SimplePostRequest simplePostRequest) {
        return postFacade.unlikePost(simplePostRequest);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable final Long id) {
        return postFacade.deletePostById(id);
    }
}
