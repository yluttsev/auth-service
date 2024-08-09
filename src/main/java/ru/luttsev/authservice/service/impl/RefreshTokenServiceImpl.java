package ru.luttsev.authservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.luttsev.authservice.exception.RefreshTokenNotFoundException;
import ru.luttsev.authservice.model.entity.RefreshToken;
import ru.luttsev.authservice.repository.TokenRepository;
import ru.luttsev.authservice.service.RefreshTokenService;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Реализация сервиса для работы с refresh токенами
 *
 * @author Yuri Luttsev
 */
@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final TokenRepository tokenRepository;

    @Override
    public RefreshToken save(RefreshToken refreshToken) {
        return tokenRepository.save(refreshToken);
    }

    @Override
    public RefreshToken getByToken(String token) {
        return tokenRepository.findByToken(token).orElseThrow(RefreshTokenNotFoundException::new);
    }

    @Override
    public void deleteTokenByUserId(UUID userId) {
        tokenRepository.deleteByUserId(userId);
    }

    @Override
    public boolean isExpired(RefreshToken token) {
        return LocalDateTime.now().isAfter(token.getExpirationDate());
    }

}
