package com.kodilla.SocialMediaApp.domain.au;

import lombok.*;

import java.time.Instant;

@Builder(toBuilder = true)
@EqualsAndHashCode
@ToString
@Getter
@AllArgsConstructor
@NoArgsConstructor
public final class AuthenticationResponse {
    private String authenticationToken;
    private String login;
    private String refreshToken;
    private Instant expiresAt;
}
