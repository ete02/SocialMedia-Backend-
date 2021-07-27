package com.kodilla.SocialMediaApp.domain.au;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Builder(toBuilder = true)
@EqualsAndHashCode
@ToString
@Getter
@AllArgsConstructor
@NoArgsConstructor
public final class RefreshTokenRequest {
    @NotBlank
    private String refreshToken;
    private String login;
}
