package com.kodilla.SocialMediaApp.repository;

import com.kodilla.SocialMediaApp.domain.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface PostRepo extends JpaRepository <Post, Long> {

    @Override
    List<Post> findAll();

    @Override
    Optional<Post> findById(final Long id);

    @Override
    Post save(final Post post);

    @Override
    void deleteById(final Long id);

    @Override
    void delete(final Post post);
}
