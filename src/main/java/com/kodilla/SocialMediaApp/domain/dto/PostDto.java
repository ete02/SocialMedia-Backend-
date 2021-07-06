package com.kodilla.SocialMediaApp.domain.dto;

import lombok.*;

import java.time.Instant;

@Builder(toBuilder = true)
@EqualsAndHashCode
@ToString
@Getter
@RequiredArgsConstructor
public final class PostDto {
    private final Long id;
    private final String postName;
    private final String headline;
    private final String url;
    private final Long imageId;
    private final Long likesCount;
    private final Instant postDate;
    private final Instant updateDate;
    private final Long userId;
    private final String login;
}
