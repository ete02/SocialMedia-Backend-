package com.kodilla.SocialMediaApp.domain.dto;

import lombok.*;

@Builder(toBuilder = true)
@EqualsAndHashCode
@ToString
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCommentRequest {
    private Long commentId;
    private String commentName;
    private String content;
}
