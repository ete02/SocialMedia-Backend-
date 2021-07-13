package com.kodilla.SocialMediaApp.repository;

import com.kodilla.SocialMediaApp.domain.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface PostRepo extends JpaRepository <Post, Long> {

    @Override
    List<Post> findAll();

    @Query(value = "SELECT * FROM POSTS ORDER BY POST_DATE DESC", nativeQuery = true)
    List<Post> findAllSorted();

    @Query(value = "SELECT * FROM POSTS P JOIN USERS U ON P.USER_ID = U.ID WHERE U.LOGIN = :login ORDER BY P.POST_DATE DESC", nativeQuery = true)
    List<Post> findAllByLoginSorted(@Param("login") final String login);

    @Override
    Optional<Post> findById(final Long id);

    @Override
    Post save(final Post post);

    @Override
    void deleteById(final Long id);

    @Override
    void delete(final Post post);
}
