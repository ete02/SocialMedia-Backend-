package com.kodilla.SocialMediaApp.domain.au;

import lombok.*;

@Builder(toBuilder = true)
@EqualsAndHashCode
@ToString
@Getter
@AllArgsConstructor
@NoArgsConstructor
public final class LoginRequest {
    private String login;
    private String password;
}
