package com.kodilla.SocialMediaApp.repository;

import com.kodilla.SocialMediaApp.domain.entity.Post;
import com.kodilla.SocialMediaApp.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Override
    List<User> findAll();

    List<User> findAllByLoginContaining(final String Login);

    @Override
    Optional<User> findById(final Long id);

    Optional<User> findByEmail(final String email);

    Optional<User> findByLogin(final String login);

    @Override
    User save(final User user);

    @Override
    void deleteById(final Long id);

    @Override
    void delete(final User user);
}