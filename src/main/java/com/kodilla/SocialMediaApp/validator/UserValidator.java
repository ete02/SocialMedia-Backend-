package com.kodilla.SocialMediaApp.validator;

import com.kodilla.SocialMediaApp.domain.dto.UserRequest;
import com.kodilla.SocialMediaApp.domain.entity.Post;
import com.kodilla.SocialMediaApp.domain.entity.User;
import com.kodilla.SocialMediaApp.service.UserServiceDb;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.kodilla.SocialMediaApp.domain.enums.UserStatus.ACTIVE;

@Slf4j
@RequiredArgsConstructor
@Component
public class UserValidator {
    private final UserServiceDb userServiceDb;

    public boolean isUserValidated(final User user) {
        return validate("Validate that user is authorized!",
                user.isEnabled(), user.getUserStatus().equals(ACTIVE));
    }

    public boolean isUserValidatedToLikePost(final User user, final Post post) {
        return validate("Validate that user is authorized and valid to like a post!",
                isUserValidated(user), !user.getLikedPosts().contains(post));
    }

    public boolean isUserValidatedToUnlikePost(final User user, final Post post) {
        return validate("Validate that user is authorized and valid to unlike a post!",
                isUserValidated(user), user.getLikedPosts().contains(post));
    }

    private boolean validate(final String info, final boolean userValidated, final boolean contains) {
        log.info(info);
        return userValidated && contains;
    }

    private boolean isNoneEmailMatch(final UserRequest userRequest) {
        log.info("Validate that email address is not already occupied!");
        return userServiceDb.getAllUsersByEmailNoneMatch(userRequest.getEmail());
    }
}