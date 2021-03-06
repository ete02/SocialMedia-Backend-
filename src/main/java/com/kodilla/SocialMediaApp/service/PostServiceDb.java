package com.kodilla.SocialMediaApp.service;

import com.kodilla.SocialMediaApp.domain.entity.Post;
import com.kodilla.SocialMediaApp.repository.PostRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PostServiceDb {
    private final PostRepo postRepository;

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public List<Post> getAllPostsSortedDescending() {
        return postRepository.findAllSorted();
    }

    public List<Post> getAllPostsByLoginSortedDescending(final String login) {
        return postRepository.findAllByLoginSorted(login);
    }

    public Optional<Post> getPostById(final Long id) {
        return postRepository.findById(id);
    }

    public Post savePost(final Post post) {
        return postRepository.save(post);
    }

    public void deletePostById(final Long id) {
        postRepository.deleteById(id);
    }

    public void deletePost(final Post post) {
        postRepository.delete(post);
    }
}
