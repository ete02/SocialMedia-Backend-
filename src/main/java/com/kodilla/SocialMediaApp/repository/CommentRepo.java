package com.kodilla.SocialMediaApp.repository;

import com.kodilla.SocialMediaApp.domain.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface CommentRepo extends JpaRepository <Comment, Long> {
    @Override
    List<Comment> findAll();

    @Override
    Optional<Comment> findById(final Long id);

    @Override
    Comment save(final Comment comment);

    @Override
    void deleteById(final Long id);

    @Override
    void delete(final Comment comment);
}

