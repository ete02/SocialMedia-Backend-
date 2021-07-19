package com.kodilla.SocialMediaApp.domain.dto;

import lombok.*;

@Builder(toBuilder = true)
@EqualsAndHashCode
@ToString
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PostRequest {
    private String login;
    private String postName;
    private String caption;
    private String url;
}
