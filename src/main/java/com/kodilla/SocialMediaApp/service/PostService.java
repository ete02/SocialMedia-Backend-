package com.kodilla.SocialMediaApp.service;

import com.kodilla.SocialMediaApp.domain.dto.PostRequest;
import com.kodilla.SocialMediaApp.domain.dto.UpdatePostRequest;
import com.kodilla.SocialMediaApp.domain.entity.Post;
import com.kodilla.SocialMediaApp.domain.entity.User;
import com.kodilla.SocialMediaApp.repository.PostRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional
@Service
public class PostService {
    private final PostServiceDb postServiceDb;

    public Post createPost(final User user, final PostRequest postRequest) {
        return postServiceDb.savePost(Post.builder()
                .postName(postRequest.getPostName())
                .caption(postRequest.getCaption())
                .url(postRequest.getUrl())
                .imageSerialNumber(user.getId())
                .likesCount(0L)
                .postDate(Instant.now())
                .user(user)
                .comments(new ArrayList<>())
                .build());
    }

    public Post updatePost(final Post post, final UpdatePostRequest updatePostRequest) {
        return postServiceDb.savePost(post.toBuilder()
                .postName(updatePostRequest.getPostName())
                .caption(updatePostRequest.getCaption())
                .updateDate(Instant.now())
                .build());
    }
}
