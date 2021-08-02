package com.kodilla.SocialMediaApp.service;

import com.kodilla.SocialMediaApp.domain.entity.User;
import com.kodilla.SocialMediaApp.domain.entity.VerificationToken;
import com.kodilla.SocialMediaApp.repository.VerificationTokenRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class VerificationTokenServiceDb {
    private final VerificationTokenRepo verificationTokenRepository;

    public List<VerificationToken> getAllVerificationTokens() {
        return verificationTokenRepository.findAll();
    }

    public Optional<VerificationToken> getVerificationTokenByToken(final String token) {
        return verificationTokenRepository.findByToken(token);
    }

    public List<VerificationToken> getUserValidVerificationToken(final User user) {
        return verificationTokenRepository.findAll().stream()
                .filter(verificationToken -> verificationToken.getUser().getId().equals(user.getId()))
                .collect(Collectors.toList());
    }

    public VerificationToken saveVerificationToken(final VerificationToken verificationToken) {
        return verificationTokenRepository.save(verificationToken);
    }

    public void deleteVerificationToken(final VerificationToken verificationToken) {
        verificationTokenRepository.delete(verificationToken);
    }
}
