package com.kodilla.SocialMediaApp.service;

import com.kodilla.SocialMediaApp.domain.entity.RefreshToken;
import com.kodilla.SocialMediaApp.repository.RefreshTokenRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class RefreshTokenServiceDb {
    private final RefreshTokenRepo refreshTokenRepository;

    public List<RefreshToken> getAllRefreshTokens() {
        return refreshTokenRepository.findAll();
    }

    public Optional<RefreshToken> getRefreshToken(final String token) {
        return refreshTokenRepository.findByToken(token);
    }

    public RefreshToken saveRefreshToken(final RefreshToken refreshToken) {
        return refreshTokenRepository.save(refreshToken);
    }

    public void deleteRefreshTokenById(final Long id) {
        refreshTokenRepository.deleteById(id);
    }

    public void deleteRefreshTokenByToken(final String token) {
        refreshTokenRepository.deleteByToken(token);
    }
}
