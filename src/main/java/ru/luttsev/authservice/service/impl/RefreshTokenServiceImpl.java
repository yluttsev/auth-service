package ru.luttsev.authservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.luttsev.authservice.exception.RefreshTokenNotFoundException;
import ru.luttsev.authservice.model.entity.AppUser;
import ru.luttsev.authservice.model.entity.RefreshToken;
import ru.luttsev.authservice.repository.TokenRepository;
import ru.luttsev.authservice.service.RefreshTokenService;

import java.time.LocalDateTime;

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
    public void deleteTokenByUser(AppUser user) {
        tokenRepository.deleteByUser(user);
    }

    @Override
    public boolean isExpired(RefreshToken token) {
        return LocalDateTime.now().isAfter(token.getExpirationDate());
    }

}
