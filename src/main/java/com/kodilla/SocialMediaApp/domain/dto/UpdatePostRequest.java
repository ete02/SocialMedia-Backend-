package com.kodilla.SocialMediaApp.domain.dto;

import lombok.*;

@Builder(toBuilder = true)
@EqualsAndHashCode
@ToString
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePostRequest {
    private Long postId;
    private String postName;
    private String caption;
}
