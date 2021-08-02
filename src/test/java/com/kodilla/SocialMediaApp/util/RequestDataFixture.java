package com.kodilla.SocialMediaApp.util;

import com.kodilla.SocialMediaApp.domain.au.LoginRequest;
import com.kodilla.SocialMediaApp.domain.au.RefreshTokenRequest;
import com.kodilla.SocialMediaApp.domain.au.RegisterRequest;
import com.kodilla.SocialMediaApp.domain.dto.*;
import com.kodilla.SocialMediaApp.domain.entity.Comment;
import com.kodilla.SocialMediaApp.domain.entity.User;
import com.kodilla.SocialMediaApp.domain.enums.RoleType;

public final class RequestDataFixture {
    private RequestDataFixture() {
    }

    public static CommentRequest createCommentRequest() {
        return CommentRequest.builder()
                .login("Test Comment")
                .content("Test Content")
                .postId(1L)
                .build();
    }

    public static UpdateCommentRequest createUpdateCommentRequest(final Comment comment) {
        return UpdateCommentRequest.builder()
                .commentId(comment.getId())
                .commentName("Test Comment")
                .content("Test Content")
                .build();
    }

    public static PostRequest createPostRequest(final String postName, final String caption) {
        return PostRequest.builder()
                .login("Test login")
                .postName(postName)
                .caption(caption)
                .url("Test url")
                .build();
    }

    public static SimplePostRequest createSimplePostRequest() {
        return SimplePostRequest.builder()
                .postId(1L)
                .login("login")
                .build();
    }

    public static UpdatePostRequest createUpdatePostRequest() {
        return UpdatePostRequest.builder()
                .postId(1L)
                .postName("Test Post")
                .caption("Test caption")
                .build();
    }

    public static UserRequest createUserRequest(final User user) {
        return UserRequest.builder()
                .userId(user.getId())
                .userName("Test User")
                .email("test@gmail.com")
                .description("Test Description")
                .build();
    }

    public static PasswordRequest createPasswordRequest(final String currentPassword, final String confirmPassword, final String newPassword) {
        return PasswordRequest.builder()
                .login("login")
                .currentPassword(currentPassword)
                .confirmPassword(confirmPassword)
                .newPassword(newPassword)
                .build();
    }

    public static RegisterRequest createRegisterRequest(final String login,
                                                        final String city, final String email, final String password) {
        return RegisterRequest.builder()
                .login(login)
                .city(city)
                .email(email)
                .password(password)
                .build();
    }

    public static RoleRequest createRoleRequest(final RoleType roleType) {
        return RoleRequest.builder()
                .login("login")
                .roleType(roleType)
                .build();
    }

    public static LoginRequest createLoginRequest() {
        return LoginRequest.builder()
                .login("login")
                .password("password")
                .build();
    }

    public static RefreshTokenRequest createRefreshTokenRequest() {
        return RefreshTokenRequest.builder()
                .login("login")
                .refreshToken("refreshToken")
                .build();
    }
}
