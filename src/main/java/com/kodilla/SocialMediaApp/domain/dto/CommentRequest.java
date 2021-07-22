package com.kodilla.SocialMediaApp.domain.dto;

import lombok.*;

@Builder(toBuilder = true)
@EqualsAndHashCode
@ToString
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CommentRequest {
    private Long postId;
    private String login;
    private String content;
}
