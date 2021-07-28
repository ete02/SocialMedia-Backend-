package com.kodilla.SocialMediaApp.service;

import com.kodilla.SocialMediaApp.domain.entity.RefreshToken;
import com.kodilla.SocialMediaApp.exceptions.security.InvalidRefreshTokenException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.List;


@RequiredArgsConstructor
@Transactional
@Service
public class RefreshTokenService {
    private final RefreshTokenServiceDb refreshTokenServiceDb;
    private final PasswordProcessorService passwordProcessorService;

    public List<RefreshToken> getRefreshTokens() {
        return refreshTokenServiceDb.getAllRefreshTokens();
    }

    public RefreshToken generateRefreshToken() {
        return refreshTokenServiceDb.saveRefreshToken(RefreshToken.builder()
                .token(passwordProcessorService.generateUuid())
                .createDate(Instant.now())
                .build());
    }

    public RefreshToken validateRefreshToken(final String token) {
        return refreshTokenServiceDb.getRefreshToken(token)
                .orElseThrow(() -> new InvalidRefreshTokenException(token));
    }

    public void deleteRefreshToken(final String token) {
        refreshTokenServiceDb.deleteRefreshTokenByToken(token);
    }
}
